/**
 * 
 */
function completeCidade(nameField, idField, baseUrl, callbackUpdater, rodizio){
	if(rodizio == undefined){
		rodizio = false;
	}
	
	nameField.typeahead({
			minLength: 2,
		    order: "asc",
		    dynamic: true,
		    delay: 500,
		    source: function (query, process) {
		        return $.ajax({
		        	url: baseUrl + '/gestao/cidade/list',
		            type: 'Get',
		            data: { maxRows: 50, query: removeDiacritics (query), rodizio: rodizio },
		            dataType: 'json',
		            global: false,
		            error: function(jqXHR, textStatus, errorThrown) 
		    		{
		    			var exceptionVO = jQuery.parseJSON(jqXHR.responseText);
		    		   
		    			$('#errorModal')
		    			.find('.modal-header h3').html(jqXHR.status+' error').end()
		    			.find('.modal-body p>strong').html(exceptionVO.clazz).end()
		    			.find('.modal-body p>em').html(exceptionVO.method).end()
		    			.find('.modal-body p>span').html(exceptionVO.message).end()
		    			.modal('show');
		    		   
		    		},
		            success: function (result) {
		            	console.log(result);
		                var resultList = result.map(function (item) {
		                	console.log(item);
		                    var aItem = { id: item.id, name: item.nome, uf: item.estado.sigla, qtde: item.entidades };
		                    return JSON.stringify(aItem);
		                });

		                return process(resultList);

		            }
		        });
		    },

			/*matcher: function (obj) {
		        var item = JSON.parse(obj);
		        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase())
		    },*/
		    
		    matcher: function (obj) {
		    	return true;
		    },
		    

		    sorter: function (items) {          
		       var beginswith = [], caseSensitive = [], caseInsensitive = [], item;
		        while (aItem = items.shift()) {
		            var item = JSON.parse(aItem);
		            if (!item.name.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(JSON.stringify(item));
		            else if (~item.name.indexOf(this.query)) caseSensitive.push(JSON.stringify(item));
		            else caseInsensitive.push(JSON.stringify(item));
		        }

		        return beginswith.concat(caseSensitive, caseInsensitive);

		    },


		    highlighter: function (obj) {
		        var item = JSON.parse(obj);
		        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
		        return item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
		            return '<font color="red"><strong>' + match + '</strong></font>';
		        }) + '/' + item.uf + (item.qtde != null ? ' (' + item.qtde + ') ':'') ;
		    },

		    updater: function (obj) {
		        var item = JSON.parse(obj);
		        idField.attr('value', item.id);
		        if(callbackUpdater){
		        	return callbackUpdater(item);
		        } else {
		        	return item.name + '/' + item.uf;
		        }
		    }
		});
};
	