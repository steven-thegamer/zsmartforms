sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchdocumentsadmin/test/integration/pages/lunchyDocumentHeadersList",
	"lunchdocumentsadmin/test/integration/pages/lunchyDocumentHeadersObjectPage",
	"lunchdocumentsadmin/test/integration/pages/lunchyDocumentItemsObjectPage"
], function (JourneyRunner, lunchyDocumentHeadersList, lunchyDocumentHeadersObjectPage, lunchyDocumentItemsObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchdocumentsadmin') + '/test/flpSandbox.html#lunchdocumentsadmin-tile',
        pages: {
			onThelunchyDocumentHeadersList: lunchyDocumentHeadersList,
			onThelunchyDocumentHeadersObjectPage: lunchyDocumentHeadersObjectPage,
			onThelunchyDocumentItemsObjectPage: lunchyDocumentItemsObjectPage
        },
        async: true
    });

    return runner;
});

