// webapp/resources/app/js/AddressesView.js

function AddressesView() {
  this.addressSize = $('fieldset.address').size();
};

AddressesView.prototype.addAddress = function() {
  var $address = $('fieldset.address');
  var newHtml = addressTemplate(this.addressSize++);
  $address.last().next().after($(newHtml));
};

AddressesView.prototype.removeAddress = function($fieldset) {
  $fieldset.next().remove(); // remove <br>
  $fieldset.remove(); // remove <fieldset>
};

function addressTemplate(number) {
  return '\
<fieldset class="address">\
    <legend>Address' + (number + 1) + '</legend>\
    <label for="addresses' + number + '.name">Name:</label>\
    <input id="addresses' + number + '.name" name="addresses[' + number + '].name" type="text" value=""/><br>\
    <label for="addresses' + number + '.postcode">Postcode:</label>\
    <input id="addresses' + number + '.postcode" name="addresses[' + number + '].postcode" type="text" value=""/><br>\
    <label for="addresses' + number + '.address">Address:</label>\
    <input id="addresses' + number + '.address" name="addresses[' + number + '].address" type="text" value=""/><br>\
    <button class="remove-address-button">Remove</button>\
</fieldset>\
<br>\
';
}

$(function() {
  var addressesView = new AddressesView();

  $('#add-address-button').on('click', function(e) {
    e.preventDefault();
    addressesView.addAddress();
  });

  $(document).on('click', '.remove-address-button', function(e) {
    if (this === e.target) {
      e.preventDefault();
      var $this = $(this); // this button
      var $fieldset = $this.parent(); // fieldset
      addressesView.removeAddress($fieldset);
    }
  });

});