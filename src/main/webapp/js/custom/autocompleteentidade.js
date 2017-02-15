/**
 * 
 */

function completeEntidade(nameField, idField, baseUrl, callbackUpdater){
	nameField.typeahead({
	    source: function (query, process) {
	        return $.ajax({
	            url: baseUrl + '/gestao/entidade/list',
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
	                	var aItem;
		                if(item.presidente != null && item.dirigente != null)
	                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: item.presidente.id, nomePresidente: item.presidente.nome, idDirigente: item.dirigente.id, nomeDirigente: item.dirigente.nome , cidade: item.cidade, uf: item.uf };
		                else if(item.presidente != null)
	                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: item.presidente.id, nomePresidente: item.presidente.nome, idDirigente: '', nomeDirigente: '', cidade: item.cidade, uf: item.uf };
		                else if(item.dirigente != null)
	                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: '', nomePresidente: '', cidade: item.cidade, uf: item.uf };
		                else
	                    	aItem = { id: item.id, name: item.razaoSocial, endereco: item.endereco, telefone: item.telefone, idPresidente: '', nomePresidente: '', cidade: item.cidade, uf: item.uf };
		                	
	                    return JSON.stringify(aItem);
	                });

	                return process(resultList);

	            }
	        });
	    },
	    
	    templates: {
	        empty: [
	          '<div class="empty-message">',
	          'unable to find any Best Picture winners that match the current query',
	          '</div>'
	        ].join('\n'),
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
	        var name = item.name.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	            return '<font color="red"><strong>' + match + '</strong></font>'
	        })
	        
	        var itm = ''
                 + "<div class='typeahead_wrapper'>"
                 //+ "<img class='typeahead_photo' src='" + name + "' />"
                 + "<div class='typeahead_labels'>"
                 + "<div class='typeahead_primary'>" + name + "</div>"
                 + "<div class='typeahead_secondary'>" + item.endereco + "</div>"
                 + "</div>"
                 + "</div>";
        	return itm;
	    },

	    updater: function (obj) {
	        var item = JSON.parse(obj);
	        idField.val(item.id);
	        if(callbackUpdater){
	        	return callbackUpdater(item);
	        } else {
	        	return item.name;
	        }
	    }
	});
};