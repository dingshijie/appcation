
$(function(){
			
		$('input[name="selectAll"]').on('click',function(){
			
			$('[name="items"]:checkbox').prop('checked',true);
		});	
		
		$('input[name="selectNor"]').on('click',function(){
			$('[name="items"]:checkbox').attr('checked',false);
		});	
		
		$('input[name="selectObs"]').on('click',function(){
//			$('[name="items"]:checkbox').each(function(){
//				
//				$(this)[0].checked=!$(this)[0].checked;
//			});
			$('[name="items"]:checkbox').prop('checked',!$('[name=items]:checkbox').prop('checked'));
		});	
		
});