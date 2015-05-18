package application.container.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * redis的服务层
 * 
 * @TODO 连续操作的时候会出现卡的情况，也不报错，具体原因未明
 * @author dingshijie
 *
 */
@Service
public class RedisService {

	@Qualifier("redisTemplate")
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private StringRedisSerializer keySerializer;

	@Autowired
	private JdkSerializationRedisSerializer valueSerializer;

	public RedisConnection getRedisConnection() {
		return redisTemplate.getConnectionFactory().getConnection();
	}

	//添加
	public boolean add(String key, String value) {

		/**
		 * setNX
		 * 将 key 的值设为 value ，当且仅当 key 不存在。
		 * 若给定的 key 已经存在，则 SETNX 不做任何动作。
		 * SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写
		 */
		RedisConnection connection = this.getRedisConnection();

		if (connection.setNX(keySerializer.serialize(key), valueSerializer.serialize(value))) {

			//SAVE 命令执行一个同步保存操作，将当前 Redis 实例的所有数据快照(snapshot)以 RDB 文件的形式保存到硬盘
			//connection.save();

			//一般来说，在生产环境很少执行 SAVE 操作，因为它会阻塞所有客户端，保存数据库的任务通常由 BGSAVE 命令异步地执行
			//在后台异步(Asynchronously)保存当前数据库的数据到磁盘
			//			connection.bgSave();

			//expire 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
			redisTemplate.expire(key, 5, TimeUnit.MINUTES);
			return true;
		} else {
			connection.discard();
			return false;
		}
	}

	//删除
	public boolean delete(String key) {
		RedisConnection connection = this.getRedisConnection();

		if (connection.exists(keySerializer.serialize(key)) && connection.del(keySerializer.serialize(key)) != null) {
			//			connection.bgSave();
			return true;
		} else {
			return false;
		}
	}

	//查询
	public Object find(String key) {
		return valueSerializer.deserialize(this.getRedisConnection().get(keySerializer.serialize(key)));
	}

	//查询数目
	public long findSize() {
		return this.getRedisConnection().dbSize();
	}

	/**
	 * 像list添加数据
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addToList(String key, String value) {

		RedisConnection connection = this.getRedisConnection();

		byte[] b = keySerializer.serialize(key);

		DataType type = connection.type(b);

		//type=NONE表示不存在，这样就不必用exist进行判断了
		if (type == DataType.NONE || type == DataType.LIST) {

			if (connection.lPush(b, valueSerializer.serialize(value)) != null) {

				//				connection.bgSave();

				redisTemplate.expire(key, 5, TimeUnit.MINUTES);
				return true;
			} else {
				return false;
			}

		}
		return false;
	}

	/**
	 * 根据key查询list中的数据
	 */
	public Object findList(String key) {

		RedisConnection connection = this.getRedisConnection();

		byte[] b = keySerializer.serialize(key);

		DataType type = connection.type(b);

		if (type == DataType.LIST) {

			//查询list的数目
			//		BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(key);
			//			return listOperations.size();

			List<Object> valueList = new ArrayList<Object>();
			List<byte[]> list = connection.lRange(keySerializer.serialize(key), 0, -1);
			for (byte[] value : list) {
				valueList.add(valueSerializer.deserialize(value));
			}

			return valueList;
		}
		return null;
	}

	//移除队头/左侧并返回列表 key 的尾元素
	public Object lPop(String key) {
		RedisConnection connection = this.getRedisConnection();
		byte[] b = keySerializer.serialize(key);

		DataType type = connection.type(b);

		if (type == DataType.LIST) {
			//返回从左侧元素，并反序列化后返回
			return valueSerializer.deserialize(connection.lPop(b));
		}
		return null;
	}

	//移除队尾/右侧并返回列表 key 的尾元素
	public Object rPop(String key) {
		RedisConnection connection = this.getRedisConnection();
		byte[] b = keySerializer.serialize(key);

		DataType type = connection.type(b);

		if (type == DataType.LIST) {
			//返回从左侧元素，并反序列化后返回
			return valueSerializer.deserialize(connection.rPop(b));
		}
		return null;
	}

	//添加到set
	public boolean addToSet(String key, String value) {

		RedisConnection connection = this.getRedisConnection();

		byte[] b = keySerializer.serialize(key);

		DataType type = connection.type(b);

		if (type == DataType.NONE || type == DataType.SET) {

			String[] values = value.split(";");

			connection.sAdd(b, valueSerializer.serialize(values));//这里序列化的方式实现原理设计没找出来，只能感觉是序列化成功了

			//			connection.bgSave();

			redisTemplate.expire(key, 5, TimeUnit.MINUTES);

			return true;
		}

		return false;
	}

	//返回key的所有成员，不存在的时候为null
	public Object findSet(String key) {

		RedisConnection connection = this.getRedisConnection();

		byte[] b = keySerializer.serialize(key);

		DataType type = connection.type(b);

		if (type == DataType.SET) {

			Set<Object> valueSet = new HashSet<Object>();

			Set<byte[]> set = connection.sMembers(b);

			Iterator<byte[]> iterator = set.iterator();
			if (iterator.hasNext()) {
				valueSet.add(valueSerializer.deserialize(iterator.next()));
			}
			return valueSet;

		}
		return null;
	}
}
