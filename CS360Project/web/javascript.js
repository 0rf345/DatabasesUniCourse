/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loginPOST() {
    var usern = $("#usern").val();
    var userp = $("#userp").val();
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'login?usern=' + usern + '&userp=' + userp);
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
    
    xhr.setRequestHeader('ContentType','application/x-www-form-urlencoded');
    xhr.send();
}

function registerPOST() {
    var usern = $("#usern").val();
    var userp = $("#userp").val();
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'register?usern=' + usern + '&userp=' + userp);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "authenticated") {
                alert("authorized");
                $("body").html("LOGGED IN");
            }else{
                alert("unauthorized");
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