/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function loginPOST() {
    if(document.getElementById("usern").checkValidity() === false) {
        alert("Please input username");
        return;
    }else if(document.getElementById("userp").checkValidity() === false) {
        alert("Please input password");
        return;
    }
    
    var usern = $("#usern").val();
    var userp = sha1($("#userp").val());
    
    var object = new Object();
    object.usern = usern;
    object.userp = userp;
    
    var request = JSON.stringify(object);
    var url = "login";
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            var jsonObj = JSON.parse(xhr.responseText);
            if(jsonObj.authstatus === "authenticated") {
                landing();
            }else if(jsonObj.authstatus === "already_authenticated"){
                alert("You have already loggen-in from another device and never logged-out.");
                loginPage();
            }else if(jsonObj.authstatus === "unauthorised") {
                alert("Username - password combo was wrong, please try again.");
                loginPage();
            }else{
                alert("Something went terribly wrong.");
                loginPage();
            }
            return;
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function landing() {
    
    whatAmIPOST();
    setTimeout(function(){
        var cltype = getCookie("cltype");
        if(cltype === "company") {
            indComPage();
            return;
        }else if(cltype === "merchant") {
            // other page
            return;
        }else if(cltype === "CCC") {
            cccPage();
            return;
        }else if(cltype === "individual"){
            indComPage();
            return;
        }else{
            alert(cltype);
            alert("Shouldn't be here.");
            return;
        }    
    }, 100); // If out of order, get the waiting time higher
       
}

function registerPOST() {
    // Every client
    if(document.getElementById("usern").checkValidity() === false) {
        alert("Please input username");
        return;
    }else if(document.getElementById("userp").checkValidity() === false) {
        alert("Please input password");
        return;
    }else if($('input[name="accountType"]:checked').length === 0) {
        alert("Please choose an account type");
        return;
    }
    
    
    var usern = $("#usern").val();
    var userp = sha1($("#userp").val());
    var client= $('input[name="accountType"]:checked').val();
    
    var object = new Object();
    var first;
    var last;
    // Company Specific
    var firsts = {};
    var lasts = {}; 
    
    if(client === 'individual') {
        object.credit = $("#credit").val();
    }
    
    if (client === 'company') {
        object.credit = $("#credit").val();
        if(document.getElementById("howMany").checkValidity() === false) {
            alert("Please input number of employees between 1 and 5");
            return;
        }
        var num = $("#howMany").val();
        object.num = num;
        object.employees = [];
        for(var i = 0; i < num; i++) {
            if(document.getElementById("first"+i).checkValidity() === false) {
                alert("Please input first name for empoyee #" +(i+1));
                return;
            }
            if(document.getElementById("last"+i).checkValidity() === false) {
                alert("Please input last name for empoyee #" +(i+1));
                return;
            }
            firsts[i] = $("#first"+i).val();
            lasts[i] = $("#last"+i).val();
            var obj = new Object();
            obj.first = firsts[i];
            obj.last = lasts[i];
            object.employees.push(obj);
        }
        if(document.getElementById("name").checkValidity() === false) {
                alert("Please input name for your company");
                return;
            }
        object.name = $("#name").val();
    }else{
        // Client is either individual or merchant
        if(document.getElementById("first").checkValidity() === false) {
            alert("Please input a first name");
            return;
        }else if(document.getElementById("last").checkValidity() === false) {
            alert("Please input a last name");
            return;
        }
        first = $("#first").val();
        last  = $("#last").val();
        object.first = first;
        object.last  = last;
    }
    
    object.usern = usern;
    object.userp = userp;
    object.client= client;
    object.action= "register";
    
    var request = JSON.stringify(object);
    var url = "register";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            var jsonObj = JSON.parse(xhr.responseText);
            if(jsonObj.regstatus === "success") {
                alert("You have successfully registered a new account.");
                loginPage();
            }else if(jsonObj.regstatus === "failure" && jsonObj.reason === "user_exists"){
                $("#usern").val("");
                return;
            }else{
                alert("Something went terribly wrong.");
                registerPage();
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function whatAmIPOST() {
    var object = new Object();
    object.action = "userkind";
    
    var request  = JSON.stringify(object);
    var url = "cs360";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything went OK
            var jsonObj = JSON.parse(xhr.responseText);
            if(jsonObj.status === "success") {
                document.cookie = "cltype="+jsonObj.client;
                return;
            }else{
                alert("Failure.");
                return;
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function buyPOST() {
    var object = new Object();
    object.action = "buy";
    object.client = getCookie("cltype");
    if(object.client === "company") {
        if(document.getElementById("employee").checkValidity() === false) {
            alert("You did not choose which employee you are.");
            return;
        }
        object.emp_id = $("#employee").val();
    }
    object.merch_id = $("#merhant").val();
    object.amount   = $("#amount").val();
    
    var request  = JSON.stringify(object);
    var url = "transaction";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything went OK
            var jsonObj = JSON.parse(xhr.responseText);
            if(jsonObj.status === "success") {
                
            }else{
                alert("Failure.");
                return;
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function getEmployeesPOST() {
    
    if(getCookie("cltype") !== "company") {
        alert("You are being naughty");
        logOutPOST();
        return;
    }
    
    var object = new Object();
    object.action = "getemployees";
    
    var request  = JSON.stringify(object);
    var url = "cs360";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything went OK
            var jsonObj = JSON.parse(xhr.responseText);
            var a = jsonObj.employees;
            if(jsonObj.status === "success") {
                var str = "";
                str += ("<select id='employee' required>");
                for(var i = 0 ; i < a.length; i++) {
                    str += ("<option value='"+a[i].id+"'>ID: #"+ a[i].id + " " +
                            a[i].fname + " " + a[i].lname +
                            "</option>");
                }
                str += ("</select>");
                $("#grabMe").html(str);
            }else{
                alert("Failure.");
                return;
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function buyPagePOST() {
    var object = new Object();
    object.action = "getmerchants";
    
    var request  = JSON.stringify(object);
    var url = "cs360";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything went OK
            
            var jsonObj = JSON.parse(xhr.responseText);
            var a = jsonObj.merchants;
            if(jsonObj.status === "success") {
                var str = "";
                str += ("<select id='merchant'>");
                for(var i = 0 ; i < a.length; i++) {
                    str += ("<option value='"+a[i].id+"'>"+
                            a[i].fname + " " + a[i].lname +
                            "</option>");
                }
                str += ("</select>");
                str += ("<p><label>Amount to pay to the merchant</label>");
                str += ("<input id=\"amount\" type=\"number\" step=\"0.01\" value=\"0.01\" min=\"0.01\" />");
                str += ("</p>");
                str += ("<p><button class=\"button3\" onclick=\"buyPOST()\">Buy Now!</button></p>");
                
                if(getCookie("cltype") === "company") {
                    getEmployeesPOST();
                    setTimeout(function(){
                        $("#grabMe").append(str);    
                    }, 100); // If out of order, get the waiting time higher
                }else{
                    $("#grabMe").html(str);
                }
                
            }else{
                alert("Failure.");
                return;
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function cccCheck() {
    if(getCookie("cltype") !== "CCC") {
        window.location.href = "https://www.youtube.com/watch?v=uO9bIx1C4Fs";
    }
}

// Redirections

function indComPage() {
    window.location.href = "buyCap.html";
}

function cccPage() {
    window.location.href = "CCC.html";
}

function buyPage() {
    window.location.href = "buySome.html";
}

function registerPage() {
    window.location.href = "register.html";
    $("#companyStuff").hide();
}

function loginPage() {
    window.location.href = "index.html";
}

function logOutPOST() {
    var object = new Object();
    object.action= "logout";
    
    var request = JSON.stringify(object);
    var url = "logout";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            var jsonObj = JSON.parse(xhr.responseText);
            if(jsonObj.logoutstatus === "success") {
                loginPage();
            }else{
                alert("You did something you shouldn't be doing.");
                return;
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
    document.cookie = "cltype=;";
}

/**
 * Delete active user IF no debt
 * @returns {undefined}
 */
function deleteMePOST() {
    var object = new Object();
    object.action= "unregister";
    
    var request = JSON.stringify(object);
    var url = "register";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                $("body").html("SUCCESS deletion of account");
            }else{
                $("body").html("You owe us money");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function payDebtPagePOST() {
    var object = new Object();
    object.action= "howMuchDoIOwe";
    
    var request = JSON.stringify(object);
    var url = "debtquestion";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                window.location.href = "payStuff.html";
                //add amount in debt right now 
            }else{
                $("body").html("You owe us money");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function payPOST() {
    var object = new Object();
    if(document.getElementById("payAmount").checkValidity() === false) {
        alert("Please pay at least 1€");
        return;
    }
    
    object.amount = $("#payAmount").val();
    
    var request = JSON.stringify(object);
    var url = "debtpay";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                $("#grabMe").html("Thanks for giving us your money.");
            }else{
                $("body").html("You owe us money");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function returnPagePOST() {
    transactionsPOST();
    // populate with transactions, check which one to return and send
    // new POST request for return
}

function transactionsPOST() {
    var object = new Object();
    object.action= "gettransactions";
    
    var request = JSON.stringify(object);
    var url = "transaction";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                $("#grabMe").html("");
                // populate with transactions
            }else{
                $("body").html("You owe us money");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

// CCC specific functions

/**
 * Should show clients that are in good sanding towards CCC
 * @returns {undefined}
 */
function goodClientsPOST() {
    var object = new Object();

    var request = JSON.stringify(object);
    var url = "goodStanding";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                // parse JSON response from back-end
                $("body").html("");
            }else{
                $("body").html("ERROR");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

/**
 * Should show clients that are in good sanding towards CCC
 * Sorted list of them
 * @returns {undefined}
 */
function badClientsPOST() {
    var object = new Object();

    var request = JSON.stringify(object);
    var url = "badStanding";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                // parse JSON response from back-end
                $("body").html("");
            }else{
                $("body").html("ERROR");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

/**
 * Give 5% discount to best merchant by CCC
 * @returns {undefined}
 */
function discountPOST() {
    var object = new Object();

    var request = JSON.stringify(object);
    var url = "discount";
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                // parse JSON response from back-end
                $("body").html("");
            }else{
                $("body").html("ERROR");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}