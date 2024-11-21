function initializeChart(labels, dataValues, backgroundColors, borderColors) {

    const labelsArray = JSON.parse(labels);
    const dataArray = JSON.parse(dataValues);
    const backgroundColorsArray = JSON.parse(backgroundColors);
    const borderColorsArray = JSON.parse(borderColors);

    const data = {
        labels: labelsArray,
        datasets: [{
            label: 'My First Dataset',
            data: dataArray,
            backgroundColor: backgroundColorsArray,
            borderColor: borderColorsArray,
            borderWidth: 1
        }]
    };
    const config = {
        type: 'bar',
        data: data,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    };

    // Render the chart
    const myChart = new Chart(
        document.getElementById('myChart'),
        config
    );
}

function initializeChart1(labels, dataValues) {

    const labelsArray = JSON.parse(labels);
    const dataArray = JSON.parse(dataValues);

    const data1 = {
        labels: labelsArray,
        datasets: [
            {
                label: "My First Dataset",
                data: dataArray,
                backgroundColor: [
                    "rgb(173,216,230)", // Light Blue
                    "rgb(135, 206, 250)", // Sky Blue
                    "rgb(176, 224, 230)", // Powder Blue
                    "rgb(100, 149, 237)", // Cornflower Blue
                    "rgb(70, 130, 180)", // Steel Blue
                    "rgb(30, 144, 255)", // Dodger Blue
                    "rgb(0, 191, 255)", // Deep Sky Blue
                    "rgb(0, 206, 209)", // Dark Turquoise
                    "rgb(0, 255, 255)", // Cyan
                    "rgb(64, 224, 208)", // Turquoise
                    "rgb(95, 158, 160)", // Cadet Blue
                    "rgb(240, 248, 255)", // Alice Blue
                ],
                hoverOffset: 4,
            },
        ],
    };

    const config1 = {
        type: "pie", // Specify the chart type here, like 'bar', 'line', etc.
        data: data1,
        options: {},
    };

    const myChart1 = new Chart(document.getElementById("myChart1"), config1);
}