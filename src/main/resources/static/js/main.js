

function arithmeticOperation(){
    var x = Number(document.getElementById("x").value);
    var source = document.getElementsByTagName("select")[0].value;
    var result
    switch (source) {
        case "solar":
            result = x * 1.8;
            break;
        case "wind":
            result = x * 1.6;
            break;
        case "hydro":
            result = x * 1.7;
            break;

    }
    document.getElementById("result").innerHTML = result;
}
