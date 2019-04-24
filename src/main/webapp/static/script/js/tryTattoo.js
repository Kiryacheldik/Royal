function playVideo(id) {
    navigator.getMedia = (navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia);

    navigator.getMedia(
        // constraints
        {video:true, audio:false},

        // success callback
        function (mediaStream) {
            var video = document.getElementById('video' + id);
            //video.src = window.URL.createObjectURL(mediaStream);
            video.srcObject = mediaStream;
            video.play();
        },
        //handle error
        function (error) {
            console.log(error);
        });
}

function closeVideo() {
    navigator.getMedia = (navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia);
    navigator.getMedia(
        // constraints
        {video:false, audio:false},

        // success callback
        function (mediaStream) {
            var video = document.getElementById('video2');
            //video.src = window.URL.createObjectURL(mediaStream);
            video.srcObject = mediaStream;
            video.off();
        },
        //handle error
        function (error) {
            console.log(error);
        });
}