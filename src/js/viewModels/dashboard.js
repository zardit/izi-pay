define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojlabel', 'ojs/ojinputtext', 'ojs/ojtable', 'ojs/ojarraytabledatasource'], function (oj, ko, $) {
    function getDataFromAPI() {
        var self = this;
        self.columnArray = '[ { "headerText": "Bill Amount", "field": "bill_amount" } ]';
        self.data = ko.observableArray();

        $.getJSON("http://127.0.0.1:3131/billingAPI/employees").
            done(function (countries) {
                var tempArray = [];
                $.each(countries, function () {
                    tempArray.push({
                        billAmount: this.id,
                        billInterest: this.bill_amount,
                        billSerialNumber: this.bill_serial_number,
                        contractNumber: this.contract_number,
                    });
                });
                self.data(tempArray);
            }).fail(function () {
                console.log("error")
            });
        self.dataSource = new oj.ArrayTableDataSource(
            self.data
        );
        

        self.filterBankReference = ko.observable();
        self.filterBillSerialNumber = ko.observable();
        self.filterContractNumber = ko.observable();
        self.filterStatus = ko.observable();

        self.handleValueChanged = function (data) {
            
            self.filterBankReference(document.getElementById('filterBankReference').rawValue);
            self.filterBillSerialNumber(document.getElementById('filterBillSerialNumber').rawValue);
            self.filterContractNumber(document.getElementById('filterContractNumber').rawValue);
            self.filterStatus(document.getElementById('filterStatus').rawValue);
            self.filterArray = ko.observableArray([]);
            self.filterArray.push({
                bankReference : self.filterBankReference(),
                billSerialNumber: self.filterBillSerialNumber(),
                status: self.filterStatus(),
                contractNumber: self.filterContractNumber()
            });
             
            var queryStringParams='?';
            for(var i=0; i< self.filterArray().length; i++)
            {
                for(var key in self.filterArray()[i])
                {
                    if(self.filterArray()[i][key] != '' && self.filterArray()[i][key] != undefined)
                    {
                       
                        queryStringParams = queryStringParams + key + '=' + self.filterArray()[i][key] + '&';
                    }
                }
                
            }
            queryStringParams = queryStringParams.substring(0, queryStringParams.length - 1);
            console.log(queryStringParams);
            $.getJSON("http://127.0.0.1:3131/billingAPI/employees"+queryStringParams).
                done(function (countries) {
                    var tempArray = [];
                    $.each(countries, function () {
                        tempArray.push({
                            billAmount: this.id,
                            billInterest: this.bill_amount,
                            billSerialNumber: this.bill_serial_number,
                            contractNumber: this.contract_number,
                        });
                    });
                    self.data(tempArray);
                }).fail(function () {
                    console.log("error")
                });
            self.dataSource = new oj.ArrayTableDataSource(
                self.data
            );
            console.log("TRUE");
        }

        self.clearClick = function (data) {
            console.log();
        }
    }
    return new getDataFromAPI();
}
);
