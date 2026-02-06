sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchydocumentsadmin/test/integration/pages/HeadersList",
	"lunchydocumentsadmin/test/integration/pages/HeadersObjectPage",
	"lunchydocumentsadmin/test/integration/pages/ItemsObjectPage"
], function (JourneyRunner, HeadersList, HeadersObjectPage, ItemsObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchydocumentsadmin') + '/test/flpSandbox.html#lunchydocumentsadmin-tile',
        pages: {
			onTheHeadersList: HeadersList,
			onTheHeadersObjectPage: HeadersObjectPage,
			onTheItemsObjectPage: ItemsObjectPage
        },
        async: true
    });

    return runner;
});

