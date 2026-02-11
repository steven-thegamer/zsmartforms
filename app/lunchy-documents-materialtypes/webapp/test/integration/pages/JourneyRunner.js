sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchydocumentsmaterialtypes/test/integration/pages/MaterialTypesList",
	"lunchydocumentsmaterialtypes/test/integration/pages/MaterialTypesObjectPage"
], function (JourneyRunner, MaterialTypesList, MaterialTypesObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchydocumentsmaterialtypes') + '/test/flpSandbox.html#lunchydocumentsmaterialtypes-tile',
        pages: {
			onTheMaterialTypesList: MaterialTypesList,
			onTheMaterialTypesObjectPage: MaterialTypesObjectPage
        },
        async: true
    });

    return runner;
});

