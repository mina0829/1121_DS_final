<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GoogleSearch</title>

<style type="text/css">
    body { 
        background-image: url('background5.jpeg');
        background-size: cover;
        margin: 0; 
        padding: 0;
    }

    .button {
        background-color: var(--background.color);
        color: black;
        position: absolute;
        width: 45px;
        height: 25px;
        font-size: 15px;
        left: 50%;
        top: 50%;
        --background.color: white;
    }

    .button:hover {
        background-color: #002ead;
        transition: 0.7s;
        color: white;
    }


    .border-style {
        border-radius: 75px/90px;
    }

    #padding {
        padding: 0px 0px 0px 15px;
    }

    .box {
        position: relative;
    }

    .box:before {
        content: '';
        position: absolute;
        z-index: 2;
        top: 60px;
        left: 50px;
        width: 50px;
        height: 50px;
        border-radius: 2px;
        transform: rotate(45deg);
        -webkit-animation: box 1.25s infinite; 
    }

    @-webkit-keyframes box {
        0% {
            top: 50px;
        }
        20% {
            border-radius: 2px;  
        }
        50% {
            top: 80px; 
            border-bottom-right-radius: 25px;
        }
        80% {
            border-radius: 2px; 
        }
        100% {
            top: 50px;
        }
    }
</style>

<script type="text/javascript">

    function click10() {
        document.getElementsByName("searchNum")[0].value = 10;
        document.getElementsByName("button10")[0].classList.toggle('clicked');
    }

    function click20() {
        document.getElementsByName("searchNum")[0].value = 20;
        document.getElementsByName("button20")[0].classList.toggle('clicked');
    }

    function click40() {
        document.getElementsByName("searchNum")[0].value = 40;
        document.getElementsByName("button40")[0].classList.toggle('clicked');
    }

    function click80() {
        document.getElementsByName("searchNum")[0].value = 80;
        document.getElementsByName("button80")[0].classList.toggle('clicked');
    }

</script>
</head>

<body>
<form action='${requestUri}' method='get'>

    <div class='box' style='position:absolute;margin-top:530px;margin-left:635px;'></div>

    <div>
        <input type='text' class="border-style" id="padding"  
            style='font-size:120%;position:absolute;left:50%;top:48%;
            margin-top:-47px;margin-left:-400px;width:800px;height:45px' 
            name='keyword' placeholder='請輸入關鍵字'
            onfocus="placeholder= '' " onblur="placeholder='請輸入關鍵字'" />
    </div>

    <div>
        <button type='button' class='button' name='button10' onclick='click10()' 
            style='border-radius:10px;cursor:pointer;margin-left:-100px'>10</button>
    </div>

    <div>
        <button type='button' class='button' name='button20' onclick='click20()' 
            style='border-radius:10px;cursor:pointer;margin-left:-47.5px'>20</button>
    </div>

    <div>
        <button type='button' class='button' name='button40' onclick='click40()' 
            style='border-radius:10px;cursor:pointer;margin-left:2.5px'>40</button>
    </div>

    <div>
        <button type='button' class='button' name='button80' onclick='click80()' 
            style='border-radius:10px;cursor:pointer;margin-left:52.5px'>80</button>
    </div>

    <div>
        <input type='image' src="search3.png" 
            style='position:absolute;width:37px;height:37px;left:50%;top:50%;
            margin-top:-55px;margin-left:368px '/>
    </div>

    <div>
        <a href ='http://localhost:8080/DSFinal1218/Main'></a>
    </div>
</form>
</body>
</html>
