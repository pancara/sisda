var site = "http://localhost:8080/demo/";

var monthNames = [ "Januari", "Februari", "Maret", "April", "Mei", "Juni",
    "Juli", "Agustus", "September", "Oktober", "November", "Desember" ];

var shortMonthNames = [ "Jan", "Feb", "Mar", "Apr", "Mei", "Jun",
    "Jul", "Agt", "Sept", "Okt", "Nov", "Des" ];

//var ffws_refresh_delay = 1 * 60 * 1000; // 1 menit ( 1 x 60 * 1000 milidetik)
var ffws_refresh_delay = 10 * 1000;

function ticker_init() {

    $("#ticker").css({
        "left": $("#header").offset().left,
        "width": $("#container").width() - 4,
        "visibility": "visible"

    });

    $('#ticker_ticker').ticker({
        titleText: 'Update &raquo; '
    });
}

// chart related
function showTooltip(x, y, contents) {
    $("<div id='plot_tooltip'>" + contents + "</div>").css({
        "font-size": "12px",
        "font-family": "Trebuchet MS",
        "font-weight": "bold",
        "color": "#CC0000",
        position: "absolute",
        display: "none",
        top: y + 5,
        left: x + 5,
        "border-radius": "4px",
        padding: "2px",
        "background-color": "#F5F5F5",
        opacity: 0.60,
        "box-shadow": "4px 2px 4px #CCC",
        "text-shadow": "4px 2px 4px #AAA"
    }).appendTo("body").fadeIn(200);
}

function plot_point_hover(event, pos, item) {
    $("#plot_tooltip").remove();
    if (item != null && item.dataIndex != null) {
        var data = item.series.data[item.dataIndex];
        var tooltip = data[1] + " m";
        showTooltip(item.pageX - 30, item.pageY - 40, tooltip);
    }
}

function plot_chart(el, chartData, options) {
    jQuery.plot(el, [chartData], options);
    $(el).bind("plothover", plot_point_hover);
}

function init_ffws_chart() {
    jQuery("div.chart_ffws_station").each(function () {
        var _div = this;
        var dataurl = $(this).attr("data-url") + "?n=15";
        jQuery.ajax({
            url: dataurl,
            type: "GET",
            dataType: "json",
            success: function (data) {
                var series = [];
                for (i = 0; i < data.length; i++) {
                    var w = data[i];
                    var samplingAt = new Date(w.samplingAt);
                    var xValue = (i == data.length - 1) || (i == 0) ?
                        moment(samplingAt).format("HH:mm<br/>D[&nbsp;]MMM[&nbsp;]YYYY") :
                        moment(samplingAt).format("HH:mm");
                    var item = [xValue, parseFloat(w.value) / 100];
                    series.push(item);
                }

                var chartData = {
                    "color": "#3366CC",
                    "label": "Tinggi Muka Air (meter)",
                    "data": series
                };
                plot_chart(_div, chartData, get_ffws_chart_options());
            }
        });
    });

}


// create map
function create_map(point, stations, station_id) {
    var $map;
    var marker_values = [];
    var $map_panel = $("#map_panel");

    $("<span>Pos :</span>")
        .appendTo($map_panel);

    var $select = $("<select></select>")
        .attr("data-marker", marker_values)
        .change(function () {
            var value = $(this).val();
            var marker = marker_values[value];
            var center = new google.maps.LatLng(marker.latLng[0], marker.latLng[1]);
            $map.gmap3("get").setCenter(center);
        })
        .appendTo($map_panel);


    for (i = 0; i < stations.length; i++) {
        var marker = {
            latLng: [stations[i].latitude, stations[i].longitude],
            data: stations[i].name,
            tag: stations[i].id
        };
        marker_values.push(marker);

        var option = $("<option></option>")
            .attr("value", i)
            .html(stations[i].name)
            .appendTo($select);
        if (station_id == stations[i].id) {
            option.attr("selected", "selected");
        }
    }


    $map_panel.detach();

    $map = $('#ffws_map').gmap3({
        panel: {
            options: {
                content: $map_panel,
                left: 90,
                top: 4
            }
        },
        map: {
            options: {
                center: [stations[0].latitude, stations[0].longitude],
                zoom: 10
            }
        },
        marker: {
            values: marker_values,
            options: {
                draggable: false
            },
            events: {
                click: function (marker, event, context) {
                    var _this = this;
                    var map = $(_this).gmap3("get");
                    var url = site + "ffws/get_data/" + context.tag + ".html?n=1";
                    $.getJSON(url, function (json) {

                        var html = context.data;

                        var w = json[0];

                        var samplingAt = new Date(w.samplingAt);
                        var strSamplingAt = moment(samplingAt).format("D MMM YYYY HH:mm ");

                        html += "<br/>" + strSamplingAt;
                        html += "<br/>" + (w.value / 100) + " m";

                        var infowindow = $(_this).gmap3({get: {name: "infowindow"}});
                        if (infowindow) {
                            infowindow.setContent(html);
                            infowindow.open(map, marker);
                        } else {
                            $(_this).gmap3({
                                infowindow: {
                                    anchor: marker,
                                    options: {content: html}
                                }
                            });
                        }
                    });
                },
                mouseout_: function () {
                    var infowindow = $(this).gmap3({get: {name: "infowindow"}});
                    if (infowindow) {
                        infowindow.close();
                    }
                }
            }
        }
    });

    if (point != null) {
        var center = new google.maps.LatLng(point.latitude, point.longitude);
        $map.gmap3("get").setCenter(center);
    }


    // set position of chart div
    $("#ffws .map .chart")
        .each(function () {
            var $this = $(this);
            $this.css({
                "left": $map.position().left + 5,
                "top": $map.position().top + $map.outerHeight() - $this.height() + 5
            });
        });

}

function init_map(station_id) {
    var url = site + "ffws/get_data/stations.html";
    if (station_id != null) {
        url += "?station=" + station_id;
    }

    $.getJSON(url, function (data) {
        create_map(data.center, data.locations, station_id);
    });
}

function get_ffws_chart_options() {
    return  {
        series: {
            lines: {
                show: true,
                barWidth: 0.6,
                align: "center",
                color: "#FF6600"

            },
            points: {
                show: true
            }
        },
        grid: {
            hoverable: true,
            clickable: true
        },
        yaxis: {
            "font": {
                color: "#000",
                size: 10,
                family: "sans-serif"
            }
        },
        xaxis: {
            mode: "categories",
            tickLength: 0,
            "font": {
                color: "#000",
                size: 10,
                family: "sans-serif",
                color: "#545454"
            }
        },
        legend: {
            show: true,
            position: "nw"
        }
    };

}

function ffws_update_side_panel() {
    var url = site + "ajax/side_panel/ffws.html";
    $.get(url, function (data) {
        $("#sidebar-right > .ffws").replaceWith($(data));
    });
    setTimeout(ffws_update_side_panel, ffws_refresh_delay);
}

function start_ffws_side_panel_updater() {
    setTimeout(ffws_update_side_panel, ffws_refresh_delay);
}

function ffws_update_chart() {
    var url = site + "ajax/ffws/chart.html";
    jQuery.get(url, function (data) {
        var $data = $(data);
        var items = $(".items", data);
        console.log(items);
        $("#ffws .items").replaceWith(items);
        init_ffws_chart();
        setTimeout(ffws_update_chart, ffws_refresh_delay);
    });
    
};

function start_ffws_update_chart() {
    ffws_update_chart();
}
