function postExpression(event) {
	event.preventDefault();
	event.stopPropagation();
	
	var postRequest = {
	        url: 'calculator/calculate',
	        type: 'post',
	        dataType: 'json',
	        data: $('form#inputForm').serialize(),
	        success: currentResult.add
    };
	
	$.ajax(postRequest);
	
	$("#expressionInput").val('');
};

function addResult($location, data) {
	$location.prepend("<div>Expression: "+data.expression+"</div><div>Result: "+data.result+"</div>")
};

var historyManager = (function() {
	var history = [];
	
	function add(calculationResult) {
		history.push(calculationResult);
		addResult($("#history"), calculationResult);
	};
	
	var instance =  {};
	instance.add = add;
	return instance;
})();

var currentResult = (function() {
	var current = null;
	
	function add (calculationResult) {		
		if (current != null) {
			historyManager.add(current);
			current = null;
		}
		var $result = $("#result");
		$result.empty();
		addResult($result, calculationResult);
		current = calculationResult;
	}
	
	var instance =  {};
	instance.add = add;
	return instance;
})();

$( window ).load(function() {
	$("#inputForm").submit(postExpression);
});