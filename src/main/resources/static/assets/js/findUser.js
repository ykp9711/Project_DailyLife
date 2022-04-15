'use strict'
function show_id(){

    document.getElementById('id_box').style.display = 'block';
    document.getElementById('pw_box').style.display = 'none';

}
function show_login_aca(){

    document.getElementById('id_box').style.display = 'none';
    document.getElementById('pw_box').style.display = 'block';

}

function emailSend() {

    $.ajax({
        url: "/user/findById",
        data: { userEmail : document.getElementById('userEmail').value } ,
        type: "GET"
    }).success(function (message)  {



        if(message === 'fail')  {
            document.getElementById('findedEmail').textContent = '존재하지 않는 이메일 입니다.'
            document.getElementById('userEmail').style.beorder = '2px solid red';
        }
    else {
            document.getElementById('findedEmail').textContent = message;
            document.getElementById('userEmail').style.beorder = '2px solid green';
        }

    })
}

function IdCheckToPw() {
    let resultDiv = document.getElementById('findedEmailToPw');
    let userIdInputDiv = document.getElementById('userIdToPw');
    $.ajax({
        url: "/user/findByIdToPw",
        data: { userId : document.getElementById('userIdToPw').value } ,
        type: "GET"
    }).success(function (message)  {

        if (message === 1) {
            resultDiv.textContent = '';
            userIdInputDiv.style.border = "0.5px solid green";
            document.getElementById('userEmailToAuth').disabled = false;
        } else {
            resultDiv.textContent = '존재하지 않는 아이디 입니다.'
            userIdInputDiv.style.border = "0.5px solid red";
            document.getElementById('userEmailToAuth').disabled = true;
        }
    })
}

function AuthEmailSend() {

    let userEmailToAuth = document.getElementById('userEmailToAuth');
    let CheckEmailDiv = document.getElementById('CheckEmailDiv');

    $.ajax({
        url: "/mail/sendEmailtoFind",
        data: { userEmail : userEmailToAuth.value } ,
        type: "GET" ,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8", //controller로 값을 넘겨줄때.
    }).success(function (message)  {

        console.log(message);

        if(message === 'complete') {
            CheckEmailDiv.textContent = '메일이 전송되었습니다.'
        }
        else {
            CheckEmailDiv.textContent = '이미 보낸 메일입니다. 60초후에 다시전송.'
        }
        document.getElementById('authorNum').disabled = false;
        document.getElementById('authorNumCheck').disabled = false;
    })

}

function emailPatternCheck() {

    let userEmailToAuth = document.getElementById('userEmailToAuth');
    let CheckEmailPatternDiv = document.getElementById('CheckEmailDiv');
    let emailSendButton = document.getElementById('emailSendButton');


    let emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

    if (emailRegExp.test(userEmailToAuth.value)) {
        userEmailToAuth.style.border = "0.5px solid green";
        emailSendButton.disabled = false;
    } else {
        CheckEmailPatternDiv.textContent = '이메일 양식을 확인해주세요.'
        userEmailToAuth.style.border = "0.5px solid red";
        emailSendButton.disabled = true;
        document.getElementById('authorNum').disabled = true;
        document.getElementById('authorNumCheck').disabled = true;
    }

}

function AuthNumSend() {

    let authorNum = document.getElementById('authorNum');

    $.ajax({
        url: "/mail/AuthNumSend",
        data: { authorNum : authorNum.value } ,
        type: "GET" ,
        // contentType: "application/x-www-form-urlencoded; charset=UTF-8", //controller로 값을 넘겨줄때.
    }).success(function (message)  {

        if(message === 'OK') {
            document.getElementById('PwChangeButton').disabled = false;
        } else {
            document.getElementById('PwChangeButton').disabled = true;
        }

    })

}



