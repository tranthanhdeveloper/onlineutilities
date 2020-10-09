$(document).ready(function() {
	const container1 = document.getElementById('jsoneditor');
	const options = {
	  modes: ['text', 'code', 'tree', 'form', 'view'],
	  mode: 'code',
	  ace: ace
	}
	const editor = new JSONEditor(container1, options);
});