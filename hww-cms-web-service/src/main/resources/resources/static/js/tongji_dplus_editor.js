(function() {
    try {
        if (dplus.track && typeof(dplus.track) == "function") {
            if (document.getElementById("jsdplus")) {
                var newsInfosrt = document.getElementById("jsdplus").innerHTML;
                var newsInfo = newsInfosrt.split(',.');
                console.log(newsInfo);
                dplus.track("Page Statistics", {
                    "Article ID": newsInfo[0],
                    "Article Title": newsInfo[1],
                    "inputtime": newsInfo[2],
                    "EditionID": newsInfo[3],
                    "Edition name": newsInfo[4],
                    "Cooperator": newsInfo[5]
                });
            }

        } else {}
    } catch (e) {}
})();