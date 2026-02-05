sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchydocumentscustomer/test/integration/pages/lunchyDocumentCustomersList",
	"lunchydocumentscustomer/test/integration/pages/lunchyDocumentCustomersObjectPage"
], function (JourneyRunner, lunchyDocumentCustomersList, lunchyDocumentCustomersObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchydocumentscustomer') + '/test/flpSandbox.html#lunchydocumentscustomer-tile',
        pages: {
			onThelunchyDocumentCustomersList: lunchyDocumentCustomersList,
			onThelunchyDocumentCustomersObjectPage: lunchyDocumentCustomersObjectPage
        },
        async: true
    });

    return runner;
});

