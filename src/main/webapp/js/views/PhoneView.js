// webapp/resources/app/js/TelefonesView.js

function TelefonesView() {
  this.telefoneSize = $('tr.telefone').size();
};

TelefonesView.prototype.addTelefone = function() {
  var $telefone = $('tr.telefone');
  var newHtml = telefoneTemplate(this.telefoneSize++);
  $telefone.last().next().after($(newHtml));
  $("#telefones tr:last").after($(newHtml));
};

TelefonesView.prototype.removeTelefone = function($fieldset) {
  $fieldset.next().remove(); // remove <br>
  $fieldset.remove(); // remove <fieldset>
};

function telefoneTemplate(number) {
	
	return '\
		<tr id="telefones' + number + '.wrapper" class="hidden telefone">\
    		<td>\
    			<select name="telefones[' + number + '].tipo" id="telefones' + number + '.tipo" class="form-control input-sm" >\
    				<option value=""></option>\
    				<option value="FIXORES">Residencial</option>\
    				<option value="FIXOCOM">Comercial</option>\
    				<option value="FIXOFAX">Fax</option>\
    				<option value="FIXOREC">Recado</option>\
    				<option value="CELULAR">Celular</option>\
    				<option value="CELREC">Celular Rec.</option>\
    			</select>\
    		</td><td>\
    			<input type="text" size="4" id="telefones' + number + '.ddd" name="telefones[' + number + '].ddd" placeholder="DDD"  class="form-control input-sm" />\
    		</td><td>\
    			<input type="text" size="10" id="telefones' + number + '.numero" name="telefones[' + number + '].numero" placeholder="Número Telefone"  class="form-control input-sm" />\
    		</td><td>\
    			<input type="text" id="telefones' + number + '.operadora" name="telefones[' + number + '].operador" placeholder="Operadora"  class="form-control input-sm" />\
    		</td><td>\
    			<input type="hidden" id="telefones' + number + '.remove" name="telefones[' + number + '].remove" value="0" />\
    			<button class="btn btn-danger btn-xs telefones.remove" data-index="' + number + '"><span class="glyphicon glyphicon-remove-sign"></span></button>\
    		</td>\
    	</tr>\
    	';
}

$(function() {
  var telefonesView = new TelefonesView();

  $('#add-telefone-button').on('click', function(e) {
    e.preventDefault();
    telefonesView.addTelefone();
  });

  $(document).on('click', '.remove-telefone-button', function(e) {
    if (this === e.target) {
      e.preventDefault();
      var $this = $(this); // this button
      var $fieldset = $this.parent(); // fieldset
      telefonesView.removeTelefone($fieldset);
    }
  });

});