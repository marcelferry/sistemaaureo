/**
 * 
 */

	function completeInstituto(nameField, idField, baseUrl){
		nameField.typeahead({
      	  
  		    source: function (query, process) {
  		        return $.ajax({
  		            url: baseUrl + '/gestao/instituto/listBase',
  		            type: 'Get',
  		            data: { maxRows: 6, query: query },
  		            dataType: 'json',
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

  		                var resultList = result.map(function (item) {
  		                    var aItem = { id: item.id, name: item.descricao };
  		                    return JSON.stringify(aItem);
  		                });

  		                return process(resultList);

  		            }
  		        });
  		    },

  			matcher: function (obj) {
  		        var item = JSON.parse(obj);
  		        return ~item.name.toLowerCase().indexOf(this.query.toLowerCase())
  		    },

  		    sorter: function (items) {          
  		       var beginswith = [], caseSensitive = [], caseInsensitive = [], item;
  		        while (aItem = items.shift()) {
  		            var item = JSON.parse(aItem);
  		            if (!item.name.toLowerCase().indexOf(this.query.toLowerCase())) beginswith.push(JSON.stringify(item));
  		            else if (~item.name.indexOf(this.query)) caseSensitive.push(JSON.stringify(item));
  		            else caseInsensitive.push(JSON.stringify(item));
  		        }

  		        return beginswith.concat(caseSensitive, caseInsensitive)

  		    },


  		    highlighter: function (obj) {
  		        var item = JSON.parse(obj);
  		        var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
  		        return item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
  		            return '<strong>' + match + '</strong>'
  		        })
  		    },

  		    updater: function (obj) {
  		        var item = JSON.parse(obj);
  		        idField.val(item.id);
  		        return item.name;
  		    }
  		});
	};