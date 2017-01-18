/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function loginPOST() {
    var usern = $("#usern").val();
    var userp = $("#userp").val();
    var xhr = new XMLHttpRequest();
    alert("a");
    xhr.open('POST', 'login?usern=' + usern + '&userp=' + userp);
    xhr.onload = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            // Everything OK
            if(xhr.responseText === "authenticated") {
                
            }else{
                $(body).html("GET OUTTA TOWN");
            }
        }
    };
}


function registerPage() {
    window.location.href = "register.html";
}