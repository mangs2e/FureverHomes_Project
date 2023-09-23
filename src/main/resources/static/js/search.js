$(document).ready(function (){
    searchGet(null,null,null);
    // searchGet(1, "", null, "");

    $("#search-form").submit(function (e){
        e.preventDefault();

        let species = $("#species").val();
        let sex = $("#sex").val();
        let region = $("#region").val();

        searchGet(species, sex, region);
    });
});

function searchGet(species, sex, region){
    let apiUrl = "/fureverhomes/animal.list"
    // apiUrl = apiUrl.replace("{page}",page);
    // apiUrl = apiUrl.replace("{size}",6);

    if(species != null)
        apiUrl += "&species=" + species;

    if(sex != null)
        apiUrl += "&sex=" + sex;

    if(region != null)
        apiUrl += "&region=" + region;

    $.ajax({
        url: apiUrl,
        type: "GET",
        async: true,
        success: function (data, status, xhr) {
            let jsonData = JSON.parse(data);
            let all_list = jsonData.animal_list;

            console.log(all_list.length)

            // let page = data.page;
            // let total = data.total;

            let dataBody = $("#animal-card");
            dataBody.empty();

            for (let i = 0; i < all_list.length; i++) {
                let animal = all_list[i];

                let link = "/fureverhomes/detail/";
                let imgSrc = "/assets/img/animal/"
                let imgId = animal.id;

                link = link + imgId;
                imgSrc = imgSrc + imgId + ".jpg";

                let div1 = $("<div>", {class: "col-12 col-md-6 mb-3"});
                let div2 = $("<div>", {class: "card border-light mb-4 animate-up-5"});
                let div3 = $("<div>", {class: "row no-gutters align-items-center"});
                let div4 = $("<div>", {class: "col-12 col-lg-6 col-xl-4"});
                let a1 = $("<a>", {href: link})
                let img = $("<img>", {
                    src: imgSrc,
                    alt: "animal-picture",
                    className: "card-img p-2 rounded-xl",
                    id: imgId,
                    style: "object-fit: cover; width: 100%; height: 230px;"
                });

                let div5 = $("<div>", {class: "col-12 col-lg-6 col-xl-8"});

                let div6 = $("<div>", {class: "card-body"});
                let div7 = $("<div>", {class: "mb-3"});

                let span1 = $("<span>", {
                        class: "h5 mb-3",
                        text: animal.name
                    }
                )
                let heartSpan = $("<span>", {style: "float: right"})

                let female = '<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-female" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M8 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8zM3 5a5 5 0 1 1 5.5 4.975V12h2a.5.5 0 0 1 0 1h-2v2.5a.5.5 0 0 1-1 0V13h-2a.5.5 0 0 1 0-1h2V9.975A5 5 0 0 1 3 5z"/></svg>';
                let male = '<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-male" viewBox="0 0 16 16">\n' +
                    '  <path fill-rule="evenodd" d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2H9.5zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8z"/>\n' +
                    '</svg>';
                let $female = $(female)
                let $male = $(male)

                let span2 = $("<span>", {
                        class: "h6 mb-3",
                        text: animal.age
                    }
                )

                let div8 = $("<div>", {class: "mb-5", style: "color:#424767;"});

                let span3 = $("<span>", {class: "fas fa-map-marker-alt mr-2"});
                let span4 = $("<span>", {text: animal.region});

                dataBody.append(div1);
                div1.append(a1);
                a1.append(div2);
                div2.append(div3);
                div3.append(div4);
                div4.append(img);
                div3.append(div5);
                div5.append(div6);
                div6.append(div7);
                div7.append(span1);
                div7.append(heartSpan);
                if (animal.sex === "F") {
                    heartSpan.append($female);
                } else if (animal.sex === "M") {
                    heartSpan.append($male)
                }
                heartSpan.append(span2);
                div6.append(div8);
                div8.append(span3);
                div8.append(span4);
            }
            // pagination(page,total);
        },error: function (xhr) {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });

    // 페이지 링크에 대한 클릭 이벤트 리스너 추가
    // $(".pagination .page-link").on("click", function (event) {
    //     event.preventDefault();
    //     let pageNumber = parseInt($(this).data("page"));
    //     if (!isNaN(pageNumber)) {
    //
    //         let name = $("#search-name").val();
    //         let interest = $("#search-interest").val();
    //         let date = $("#reserve-date").val();
    //
    //         searchGet(pageNumber, name, interest, date);
    //     }
    // });
}