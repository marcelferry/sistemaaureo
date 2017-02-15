
//Flot Pie Chart
$(function() {

    var data = [{
        label: "Norte",
        data: 2
    }, {
        label: "Sul",
        data: 6
    }, {
    	label: "Nordeste",
    	data: 3
    }, {
        label: "Sudeste",
        data: 9
    }, {
        label: "Centro Oeste",
        data: 20
    }];

    var plotObj = $.plot($("#flot-pie-chart"), data, {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: true
        }
    });

});

//Flot Pie Chart
$(function() {
	
	var data = [{
		label: "Atingidas",
		data: 5
	}, {
		label: "A cumprir",
		data: 7
	}, {
		label: "N&atilde;o Cumpridas",
		data: 7
	}];
	
	var plotObj = $.plot($("#flot-pie-chart"), data, {
		series: {
			pie: {
				show: true
			}
		},
		grid: {
			hoverable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
			shifts: {
				x: 20,
				y: 0
			},
			defaultTheme: false
		}
	});
	
});

//Flot Pie Chart
$(function() {
	
	var data = [{
		label: "Atingidas",
		data: 3
	}, {
		label: "A cumprir",
		data: 5
	}, {
		label: "N&atilde;o Cumpridas",
		data: 1
	}];
	
	var plotObj = $.plot($("#flot-pie-chart2"), data, {
		series: {
			pie: {
				show: true
			}
		},
		grid: {
			hoverable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
			shifts: {
				x: 20,
				y: 0
			},
			defaultTheme: false
		}
	});
	
});

//Flot Pie Chart
$(function() {
	
	var data = [{
		label: "Atingidas",
		data: 3
	}, {
		label: "A cumprir",
		data: 1
	}, {
		label: "N&atilde;o Cumpridas",
		data: 6
	}];
	
	var plotObj = $.plot($("#flot-pie-chart3"), data, {
		series: {
			pie: {
				show: true
			}
		},
		grid: {
			hoverable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
			shifts: {
				x: 20,
				y: 0
			},
			defaultTheme: false
		}
	});
	
});

//Flot Pie Chart
$(function() {
	
	var data = [{
		label: "Atingidas",
		data: 6
	}, {
		label: "A cumprir",
		data: 9
	}, {
		label: "N&atilde;o Cumpridas",
		data: 2
	}];
	
	var plotObj = $.plot($("#flot-pie-chart4"), data, {
		series: {
			pie: {
				show: true
			}
		},
		grid: {
			hoverable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
			shifts: {
				x: 20,
				y: 0
			},
			defaultTheme: false
		}
	});
	
});

//Flot Pie Chart
$(function() {
	
	var data = [{
		label: "Atingidas",
		data: 8
	}, {
		label: "A cumprir",
		data: 13
	}, {
		label: "N&atilde;o Cumpridas",
		data: 4
	}];
	
	var plotObj = $.plot($("#flot-pie-chart5"), data, {
		series: {
			pie: {
				show: true
			}
		},
		grid: {
			hoverable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
			shifts: {
				x: 20,
				y: 0
			},
			defaultTheme: false
		}
	});
	
});

//Flot Pie Chart
$(function() {
	
	var data = [{
		label: "Atingidas",
		data: 7
	}, {
		label: "A cumprir",
		data: 3
	}, {
		label: "N&atilde;o Cumpridas",
		data: 5
	}];
	
	var plotObj = $.plot($("#flot-pie-chart6"), data, {
		series: {
			pie: {
				show: true
			}
		},
		grid: {
			hoverable: true
		},
		tooltip: true,
		tooltipOpts: {
			content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
			shifts: {
				x: 20,
				y: 0
			},
			defaultTheme: false
		}
	});
	
});

