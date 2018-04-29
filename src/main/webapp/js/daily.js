function highlightOnOntherTable(y,tableName){
	var x = [];
	x = y.split("-");
	for(var z=0;z<x.length;z++){
		 $('#'+tableName+' tr').each(function(){
			 	if(tableName == "playedGames") {
			 		if($(this).find('td').eq(1).text().trim() == x[z] || $(this).find('td').eq(3).text().trim() == x[z]){
			            $(this).css('background','skyblue');
			        } else if($(this).find('td').eq(1).text().trim().includes(x[z]) || $(this).find('td').eq(3).text().trim().includes(x[z])){
			        	$(this).css('background','skyblue');
			        }
			 	} else if(tableName == "palyerByRating") {
			 		if($(this).find('td').eq(1).text().trim() == x[z]){
			            $(this).css('background','skyblue');
			        }
			 	}
		        
		 });
	}	
}

function removeHighlightedCssFromTable(tableName){
	$('#'+tableName+' tr').each(function(){
		$(this).css('background','');
 });
}