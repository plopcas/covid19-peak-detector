var casesGraph;
var chatChannel;
var messageId = 0;

$(document).ready(function () {
    // alerts
    $('#countries').change(function() {
        $('#default-image').hide();
        getCountryData();
    });
    $('#create-alert-btn').click(function() {
        createAlert();
    });
    // chat
    Twilio.Chat.Client.create(token).then(chatClient => {
        getChannelDescriptor(chatClient)
            .then(channel => channel.getChannel())
            .then(channel => channel.join())
            .then(channel => {
                chatSetupCompleted();
                chatChannel = channel;
                channel.on("messageAdded", onMessageAdded);
                activateChatBox();
            });
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

function getChannelDescriptor(chatClient) {
  return chatClient
    .getPublicChannelDescriptors()
    .then(function(paginator) {
      if (paginator.items.length > 0) return paginator.items[0];
      else {
        chatClient
          .createChannel({
            uniqueName: "general",
            friendlyName: "General Chat Channel"
          })
          .then(function(newChannel) {
            console.log("Created general channel:");
            console.log(newChannel);
            return newChannel;
          });
      }
    })
    .then(channel => channel)
    .catch(error => console.log("error getting channel", error) || error);
}

function chatSetupCompleted() {
  let template = $("#new-message").html();
  template = template.replace(
    "{{body}}",
    "<b>Chat Setup Completed. Start your conversation!</b>"
  );
  $(".chat").append(template);
}

function onMessageAdded(message) {
  let template = $("#new-message").html();
  template = template.replace(
    "{{body}}",
    `<b>${message.author}:</b> ${message.body}`
  );
  template = template.replace(
    "{{id}}",
    `message-` + messageId
  );
  $(".chat").append(template);
  $('.card-body').scrollTo('#message-' + messageId);
  messageId++;
}

function activateChatBox() {
    $("#message").removeAttr("disabled");
    $("#btn-chat").click(function() {
        const message = $("#message").val();
        $("#message").val("");
        chatChannel.sendMessage(message);
    });
    $("#message").on("keydown", function(e) {
        if (e.keyCode === 13) {
          $("#btn-chat").click();
        }
    });
}