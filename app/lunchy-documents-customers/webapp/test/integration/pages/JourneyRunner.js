sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchydocumentscustomers/test/integration/pages/CustomersList",
	"lunchydocumentscustomers/test/integration/pages/CustomersObjectPage"
], function (JourneyRunner, CustomersList, CustomersObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchydocumentscustomers') + '/test/flpSandbox.html#lunchydocumentscustomers-tile',
        pages: {
			onTheCustomersList: CustomersList,
			onTheCustomersObjectPage: CustomersObjectPage
        },
        async: true
    });

    return runner;
});

