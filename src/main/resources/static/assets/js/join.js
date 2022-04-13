$('#userId').on("propertychange change keyup paste input", function(){
    var userId = $('#userId').val(); // .id_input에 입력되는 값
    var data = {userId : userId} // '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)'
    var check = false;
    $.ajax({ type : "get",
        url : "/user/checkId",
        data : data,
        success : function(result){
            $('#Check_Id').css('display', 'block');
            if(result != 'fail'){
                check = true;
                $("#Check_Id").css("color","black");
                $("#Check_Id").text("사용할 수 있는 아이디입니다.");
                $("#userNickName").attr("disabled", false);
            }
            else {
                $("#Check_Id").css("color","red");
                $("#Check_Id").text("중복된 아이디입니다.");
                $("#userNickName").attr("disabled", true);
                $("#userPassword").val("");
                $("#userPassword").attr("disabled", true);
                $("#userPasswordCheck").attr("disabled", true);
                $("#userPasswordCheck").val("");
                $('#userEmail').attr('disabled',true);
                $('#emailButton').attr('disabled',true);
                $('#emailAuthor').attr('disabled',true);
                $('#authorButton').attr('disabled',true);
                $('#join').attr('disabled',true);
            }
        }// success 종료
    }); // ajax 종료
});// function 종료

$('#userNickName').on("propertychange change keyup paste input", function(){
    var userNickName = $('#userNickName').val(); //
    var data = {userNickName : userNickName} // '컨트롤에 넘길 데이터 이름' : '데이터(.id_input에 입력되는 값)
    $.ajax({ type : "get",
        url : "/user/checkNickName",
        data : data,
        success : function(result){
            $('#Check_NickName').css('display', 'block');
            if(result != 'fail'){
                $("#Check_NickName").css("color","black");
                $("#Check_NickName").text("사용할 수 있는 닉네임입니다.");
                $("#userPassword").attr("disabled", false);

            }
            else {
                $("#Check_NickName").css("color","red");
                $("#Check_NickName").text("중복된 닉네임입니다.");
                $("#userPassword").val("");
                $("#userPassword").attr("disabled", true);
                $("#userPasswordCheck").val("");
                $("#userPasswordCheck").attr("disabled", true);
                $('#userEmail').attr('disabled',true);
                $('#emailButton').attr('disabled',true);
                $('#emailAuthor').attr('disabled',true);
                $('#authorButton').attr('disabled',true);
                $('#join').attr('disabled',true);

            }
        }// success 종료
    }); // ajax 종료
});// function 종료

$('#userPassword').on("propertychange change key up paste input",function(){ // 비밀번호 정규식
    let reg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    let pw = $('#userPassword').val();
    if(!reg.test(pw)) { // 입력한 비밀번호가 정규식에 어긋나면
        $('#pw').css('display', 'block');
        $("#pw").css("color","red");
        $('#pw').text("* 숫자와 문자가 섞인 최소 8자 이상이여야 합니다.")
        $('#userEmail').attr('disabled', true);
        $('#emailButton').attr('disabled',true);
        $('#emailAuthor').attr('disabled',true);
        $('#authorButton').attr('disabled',true);
        $('#join').attr('disabled',true);
    }else{
        $('#pw').css('display', 'none');
        $('#userPasswordCheck').attr('disabled', false);
    }
});

$('#userPasswordCheck').on("propertychange change key up paste input",function(){ // 비밀번호 재확인 확인
    let pw = $('#userPassword').val();
    let checkPw = $('#userPasswordCheck').val();
    if(pw!==checkPw){
        $('#Check_Pw').css('display', 'block');
        $("#Check_Pw").css("color","red");
        $('#Check_Pw').text("* 비밀번호가 일치하지 않습니다.")
        $('#userEmail').attr('disabled',true);
        $('#emailButton').attr('disabled',true);
        $('#emailAuthor').attr('disabled',true);
        $('#authorButton').attr('disabled',true);
        $('#join').attr('disabled',true);
    }else {
        $('#Check_Pw').css('display','none');
        $('#userEmail').attr('disabled',false);
    }
});

$('#userEmail').on('propertychange change key up paste input',function(){
    let reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    var email = $('#userEmail').val();
    if(!reg.test(email)){
        console.log("들어옴")
        $('#Check_Email').css('display', 'block');
        $("#Check_Email").css("color","red");
        $('#Check_Email').text("* 이메일 형식에 맞지 않습니다.")
        $("#userPassword").attr("disabled", true);
        $('#emailButton').attr('disabled',true);
        $('#emailAuthor').attr('disabled',true);
        $('#authorButton').attr('disabled',true);
        $('#join').attr('disabled',true);
    }else {
        $('#Check_Email').css('display', 'none');
        $('#emailButton').attr('disabled',false);
        $('#Check_Author').attr('disabled',false);
        $('#emailAuthor').attr('disabled',false);
    }
});

$(function(){
    $('#emailButton').on('click',function(){
        $('#authorButton').attr('disabled',false);
    })
});

function emailAuthorCheck() {
    let emailAuthor=$("#emailAuthor").val();
    let messageDTO={
        emailAuthor:emailAuthor ,
    };
    $.ajax({
        url: "/user/emailAuthorCheck",
        data: messageDTO,
        type:"POST",
    }).success(function (message2) {
        let key = $.trim(message2.key);
        if(key == 'success') {
            $("#emailCheckText").text(message2.msg);
            $('#join').attr('disabled',false);
        }else if (key == 'fail') {
            $("#emailCheckText").text(message2.msg);
            $('#join').attr('disabled',true);
        }
        // $("#pwCheckResult").replaceWith(fragment);
    });
}

function emailAuthorSend() {
    let userEmail=$("#userEmail").val();
    let messageDTO={
        userEmail:userEmail ,
    };
    $.ajax({
        url: "/user/EmailAuthor",
        data: messageDTO,
        type:"GET",
    }).success(function (message) {
        const key = $.trim(message.key);
        if(key == 'succese') {
            console.log(message.msg)
            $("#emailSendText").replaceAll(message.msg);
            $('#')
        }
        if(key == 'emailDuplicate') {
            console.log(message.msg)
            $("#emailSendText").text(message.msg);
        }
        else {
            console.log(message.msg)
            $("#emailSendText").text(message.msg);
        }

    });
}