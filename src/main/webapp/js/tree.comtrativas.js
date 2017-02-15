var colunaLinks = 0;

        function naoExibir_ComTratativas(idbox) {
            var nodes = document.getElementById(idbox).childNodes;
            for (var i = 0; i < nodes.length; i++) {
                var obj = nodes[i];
                if (obj.id) {
                    obj.style.display = "block";
                    break;
                }
            }
        }

        function getElements_ComTratativas(idbox) {
            var nodes = document.getElementById(idbox).childNodes;
            var names = new Array();
            for (var i = 0; i < nodes.length; i++) {
                var obj = nodes[i];
                if (obj.id)
                    names.push(nodes[i].id);
            }
            return names;
        }

        function showChildren_ComTratativas(elem, img) {
            var SEP = "_";
             
            var children = getElements_ComTratativas('ComTratativas'); 
            for (var i = 0; i < children.length; i++) {
                if (children[i].split(SEP)[1] == elem.split(SEP)[0]) {
                    var localObj = document.getElementById(children[i]);
                    var status = localObj.style.display;
                    if (status == "table-row") {
    					localObj.style.display = "none";
    					hideChildren_ComTratativas(localObj.id, children, img);
    					img.src = img.src.replace("details_close.png","details_open.png");
                    } else {
                        localObj.style.display = "table-row";
    					img.src = img.src.replace("details_open.png","details_close.png");
                    }
    				reOpenNodes(localObj);
                }
            }
        }
    	function reOpenNodes(obj){
    		var nodeTd = obj.getElementsByTagName("td")[colunaLinks];
    		var imgs = nodeTd.getElementsByTagName("img");
    		
    		for (var j = 0; j < imgs.length; j++) {
    			img2 = imgs[j];
    			img2.src = img2.src.replace("details_close.png","details_open.png");
    		}
    		
    		var SEP = "_";
            var children = getElements_ComTratativas('ComTratativas'); 
    		elem = obj.id;
            for (var i = 0; i < children.length; i++) {
    		//alert(children[i].split(SEP)[1] +"  "+parent.id.split(SEP)[1]);
                if (children[i].split(SEP)[1] == elem.split(SEP)[0]) {
    			 var localObj = document.getElementById(children[i]);
                    return reOpenNodes(localObj);//rever
    			}
    		}
    		
    	}

        function hideChildren_ComTratativas(elem, children, img) {
            var SEP = "_"; 

            for (var i = 0; i < children.length; i++) {

                if (children[i].split(SEP)[1] == elem.split(SEP)[0]) {
                    var localObj = document.getElementById(children[i]);
                    var status = localObj.style.display;
                    if (status == "table-row") {
                        localObj.style.display = "none";
                        hideChildren_ComTratativas(localObj.id, children, img);
    					img.src = img.src.replace("details_close.png","details_open.png");
                    }
                }
            }
        }