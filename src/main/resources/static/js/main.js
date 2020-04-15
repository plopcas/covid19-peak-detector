var casesGraph;

$(document).ready(function () {
    $('#countries').change(function() {
        $('#default-image').hide();
        getCountryData();
    });
    $('#create-alert-btn').click(function() {
        createAlert();
    });
});

function drawCasesGraph(id, title, country, cases) {
     casesGraph = new Chart(id, {
        type: 'line',
        data: {
            xLabels: Object.keys(cases),
            datasets: [{
                label: [country],
                data: Object.values(cases),
                backgroundColor: [
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            title: {
                display: true,
                text: title
            },
            scales: {
                 yAxes: [{
                     ticks: {
                         beginAtZero: true,
                         maxTicksLimit: 4
                     }
                 }]
             }
        }
    });
}

function getCountryData() {
    var country = $('select option:selected').text().trim();

    if (casesGraph) {
        casesGraph.destroy();
    }

    $.ajax({
        url: '/countries/' + country,
        type: 'GET',
        contentType: 'application/json;charset=utf-8',
        success: function (res) {
            var cases = res.timeline.cases;
            drawCasesGraph('cases-graph', 'COVID-19 Total Cases', country, cases);
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function createAlert() {
    var country = $('select option:selected').text().trim();
    var phone = $('#phone').val().trim();

    var alertData = {
        "country": country,
        "phone": phone
    };

    $.ajax({
        url: '/alerts',
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(alertData),
        success: function (res) {
            alert("OK");
        },
        error: function (err) {
            alert("Error");
            console.log(err);
        }
    });
}
