var firebaseConfig = {
    apiKey: "AIzaSyC4UnKMMBgCpCBr7JI1qQI-dycpE9wALGs",
    authDomain: "photoapp-6ea36.firebaseapp.com",
    databaseURL: "https://photoapp-6ea36-default-rtdb.europe-west1.firebasedatabase.app",
    projectId: "photoapp-6ea36",
    storageBucket: "photoapp-6ea36.appspot.com",
    messagingSenderId: "406951612164",
    appId: "1:406951612164:web:2947920c422fddadb0ca51"
};
firebase.initializeApp(firebaseConfig);

window.onload = function () {
    let p = document.getElementById("loginP");
    let form = document.getElementById("loginForm");
    let loginBtn = document.getElementById("loginBtn");

    if (localStorage.getItem("userEmail") != null) {
        p.innerHTML = "Welcome " + localStorage.getItem("userEmail") + "!";
        loginBtn.innerText = "Logout";
    }

    loginBtn.onclick = function () {
        if (localStorage.getItem("userEmail") != null) {
            localStorage.clear();
            loginBtn.innerText = "Login";
            p.innerHTML = "";
        }
    }


    form.addEventListener('submit',function(evt){
        evt.preventDefault();
        let login = document.getElementById("login").value;
        let pass = document.getElementById("pass").value;
        if (login != null && pass != null) {
            firebase.auth().signInWithEmailAndPassword(login, pass)
                .then((userCredential) => {
                    var user = userCredential.user;
                    p.innerHTML = "Welcome " + user.email;
                    localStorage.setItem("userEmail", user.email);
                    loginBtn.innerText = "Logout";
                    // firebase.database().ref('users/' + user.uid).set({
                    //     role : "moderator"
                    // });
                    var starCountRef = firebase.database().ref('users/' + user.uid);
                    starCountRef.on('value', (snapshot) => {
                        const data = snapshot.val();
                        if (data.role.toString() == "moderator") {
                            //=============================================================================!!!
                            localStorage.setItem("role", data.role.toString());
                        }
                    });

                })
                .catch((error) => {
                    var errorCode = error.code;
                    var errorMessage = error.message;
                });
        }
        $(".div_popup_overlay").css({'display':'none'})
        $("#loginForm").addClass("hide")
    })
}
