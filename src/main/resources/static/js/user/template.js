function toggleSidebar() {
    console.log("working");
    const topnav = document.getElementById("topnav");
    const sidenav = document.getElementById("sidenav");

    if(topnav.style.display != "none") {
        topnav.style.display = "none";
        sidenav.style.display = "block";
    }
    else {
        sidenav.style.display = "none";
        topnav.style.display = "flex";
    }
}

function showSearchResult() {
    const searchResult = document.getElementById("search-result");
    const query = document.getElementById("search").value;
    
    if(query == '')
        searchResult.style.display = "none";
    else { 
        let url = `http://localhost:8081/search/${query}`;

        fetch(url).then((response) => {
            return response.json();
        })
        .then((data) => {
            console.log(data);
            
            searchResult.innerHTML = '';

            data.forEach(element => {
                const para = document.createElement("p");   

                var a = document.createElement('a');
                a.innerHTML = element.name;
                a.href = `http://localhost:8081/user/product/${element.id}`;
                para.append(a);

                searchResult.append(para);
            });
        });

        searchResult.style.display = "block";
    }
}