$(function() {

	$.fn.isDigito = function(e) {
		if (!e) e = window.event;
		if (e.keyCode) code = e.keyCode;
		if (e.which) code = e.which;
		var c = String.fromCharCode(code);
		var reDigits = /^\d+$/;
		return reDigits.test(c);
	};


	$.fn.setMaskMoeda = function(v) {
		//retira a formatação
		v = v.replace(/\D/g, "");

		var len = v.length;
        if (1== len)
        	v = v.replace(/(\d)/,"0,0$1");
        else if (2 == len)
        	v = v.replace(/(\d)/,"0,$1");
        else if (len > 2){
        	var r = "(\\d)";
            var m = "$1";
            var j = 2;
            for (var i=3;i<(len-2);i+=3){
            	r += "(\\d{3})";
            	if (j>1)
            		m+=".";
            	m += "$"+ j;
            	j+=1;
            }
            if (j>1){
            r += "(\\d{2})$";
            m += ",$" + j;
            reg = new RegExp(r);
            v = v.replace(reg,m);
            }else{
            	v = v.replace(/(\d{2})$/ ,',$1');
            }
        }

//        (\d{3})(\d{3})(\d{3})(\d{3})(\d{3})(\d{2})$
//        $1.$2.$3.$4.$5,$6
		if (v != "" && v.charAt(0) == '0' && v.charAt(1) != '\,'){
			var inicio = 0;
			inicio++;
			for (var i=1;i<v.length;i++){
				if (v.charAt(i+1) != '\,' && v.charAt(i) == '0'){
					inicio++;
				} else {
					i = v.length;
				}
			}
			v = v.substring(inicio,v.length);
		}
		$(this).val(v);
	//	$(this).setCursorPosition($(this).lastIndexOf());
	};

	$('body').on('keyup', '.moeda', function (){
		$(this).setMaskMoeda($(this).val());
	});

	$('body').on('keypress', '.moeda', function (){
		if(e.which != '13'){
			if (!(e.which == '8' || e.which == "9" || e.which == "0")) {
				if (!($(this).isDigito(e))) {
					e.preventDefault();
					return false;
				}
			}
		}
	});

	$('body').on('keydown', '.moeda', function (){
		$(this).setMaskMoeda($(this).val());
	});

	$('body').on('blur', '.moeda', function (){
		if ($(this).val() == "0,00") {
			$(this).val("");
		} else {
			$(this).change();
		}
	});

	$('body').on('focus', '.moeda', function (){
		if ($(this).val().length == 0) {
			$(this).val("0,00");
		//	$(this).setCursorPosition($(this).lastIndexOf());
		}
	});

});