/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loginPOST() {
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
            if(xhr.responseText === "authenticated") {
                alert("authorized");
                $("body").html("LOGGED IN");
            }else{
                alert("unauthorized");
                $("body").html("GET OUTTA TOWN");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/json');
    xhr.send(request);
}

function registerPOST() {
    // Every client
    var usern = $("#usern").val();
    var userp = sha1($("#userp").val());
    alert(userp);
    var name  = $("#clientName").val();

    // Company Specific
    var firsts = {};
    var lasts = {}; 
    if ($('input[name="accountType"]:checked').val() === 'company') {
        var num = $("#howMany").val();
        for(var i = 0; i < num; i++) {
            firsts[i] = $("#first"+i).val();
            lasts[i] = $("#last"+i).val();
        }
    }
    
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'register?usern=' + usern + '&userp=' + userp + '&name=' + name);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "OK") {
                $("body").html("SUCCESS registering new account");
            }else{
                $("body").html("Something went terribly wrong");
            }
        }
    };
    
    xhr.setRequestHeader('ContentType','application/x-www-form-urlencoded');
    xhr.send();
}



function registerPage() {
    window.location.href = "register.html";
    $("#companyStuff").hide();
}

function loginPage() {
    window.location.href = "index.html";
}