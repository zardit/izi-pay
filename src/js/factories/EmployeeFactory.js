define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojtable', 'ojs/ojarraytabledatasource'], function(oj, ko, $) {
    function getDataFromAPI() {
        var self = this;        
        self.data = ko.observableArray();
        $.getJSON("http://127.0.0.1:3131/billingAPI/employees").
            then(function(countries) {
                var tempArray = [];
                $.each(countries, function() {
                    tempArray.push({
                        name: this.name, 
                        population: this.population
                    });
                });
                self.data(tempArray);
        });
        self.dataSource = new oj.ArrayTableDataSource(
            self.data,
            {idAttribute: 'id'}
        );
    }
    return new getDataFromAPI();
    }
);
