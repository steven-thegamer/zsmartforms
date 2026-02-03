sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchydocumentsmain/test/integration/pages/lunchyDocumentHeadersList",
	"lunchydocumentsmain/test/integration/pages/lunchyDocumentHeadersObjectPage",
	"lunchydocumentsmain/test/integration/pages/lunchyDocumentItemsObjectPage"
], function (JourneyRunner, lunchyDocumentHeadersList, lunchyDocumentHeadersObjectPage, lunchyDocumentItemsObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchydocumentsmain') + '/test/flpSandbox.html#lunchydocumentsmain-tile',
        pages: {
			onThelunchyDocumentHeadersList: lunchyDocumentHeadersList,
			onThelunchyDocumentHeadersObjectPage: lunchyDocumentHeadersObjectPage,
			onThelunchyDocumentItemsObjectPage: lunchyDocumentItemsObjectPage
        },
        async: true
    });

    return runner;
});

