
    function pushNotify(title, body) {
        var notification = new Notification(title, {
            body: body
        });

        notification.onclick = function () {
            window.open(document.URL);
        };

        // notification.onclose = function () {
        // 	rice = 0
        // 	globalThis(rice);
        // }

        setTimeout(notification.close.bind(notification), 20000);
    }

    if ( !("Notification" in window)) {
        alert("This browser does not support desktop notification")
    }
    else if (Notification.permission === "granted") {
        console.log("Push notification running");
        // pushNotify( "Welcome to SchoolScape", "This is our push notification service" )
    }
    else if (Notification.permission !== "denied") {
        console.log("Egg")
        Notification.requestPermission().then(function(result) {
            if (result === "granted") {
                pushNotify( "Welcome to School Scape", "This is our push notification service" )
            }
        });
    }