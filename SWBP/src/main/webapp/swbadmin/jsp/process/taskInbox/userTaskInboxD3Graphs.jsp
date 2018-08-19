<%-- 
    Document   : userTaskInboxD3Graphs
    Created on : 30/07/2013, 12:57:38 PM
    Author     : Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
--%>
<%@page import="org.semanticwb.portal.api.SWBParamRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
String processData = (String) request.getAttribute("processStats");
%>
<div class="row">
    <div class="col-sm-4">
        <div class="panel panel-default">
            <div class="panel-heading">Desempeño</div>
            <div class="panel-body">
                <div id="performanceGraph" class="row">
                    <div class="col-xs-12"><p class="text-center">Sin información</p></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-4">
        <div class="panel panel-default">
            <div class="panel-heading"><%=paramRequest.getLocaleString("lblResponsetime")%></div>
            <div class="panel-body">
                <div id="responseTime" class="row">
                    <div class="col-xs-12"><p class="text-center">Sin información</p></div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-4">
        <div class="panel panel-default">
            <div class="panel-heading">Estatus</div>
            <div class="panel-body">
                <div id="overdueGraph" class="row">
                    <div class="col-xs-12"><p class="text-center">Sin información</p></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    (function() {
        var tpl = "<div class='col-xs-4 chartContainer'></div><div class='col-xs-8 chartLabel'></div>";
        var lblTpl = "<div class='col-xs-12 text-left'><span>_labelVal_</span> _labelText_</div>";
        var createDonutChart = function(chartData, chartHolder) {
            if (chartData) {
                $("#"+chartHolder).empty();
                $("#"+chartHolder).append($(tpl));
                var chart = c3.generate({
                    data : {
                        columns: chartData,
                        type: "donut"
                    },
                    legend: {
                      show: false  
                    },
                    donut: {
                        label: {
                            show: false
                        }
                    },
                    size:{height:60},
                    bindto:$("#"+chartHolder+" .chartContainer")[0]
                });

                $("#"+chartHolder+" .chartLabel").append(lblTpl.replace("_labelVal_", chartData[0][1]).replace("_labelText_", chartData[0][0]));
                if (chartData[1]) {
                    $("#"+chartHolder+" .chartLabel").append(lblTpl.replace("_labelVal_", chartData[1][1]).replace("_labelText_", chartData[1][0]));
                }
            }
        };
        
        var processInfoStats = <%= processData %>;
        
        $(document).ready(function() {
            if (window.c3 && window.c3.version) {
                processInfoStats.responseData && createDonutChart(processInfoStats.responseData, "responseTime");
                processInfoStats.statusData && createDonutChart(processInfoStats.statusData, "overdueGraph");
                processInfoStats.instanceData && createDonutChart(processInfoStats.instanceData, "performanceGraph");
            }
        });
    })();
</script>