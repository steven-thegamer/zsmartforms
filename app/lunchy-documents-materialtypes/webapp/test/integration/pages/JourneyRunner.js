sap.ui.define([
    "sap/fe/test/JourneyRunner",
	"lunchydocumentsmaterialtypes/test/integration/pages/lunchyDocumentMaterialTypesList",
	"lunchydocumentsmaterialtypes/test/integration/pages/lunchyDocumentMaterialTypesObjectPage"
], function (JourneyRunner, lunchyDocumentMaterialTypesList, lunchyDocumentMaterialTypesObjectPage) {
    'use strict';

    var runner = new JourneyRunner({
        launchUrl: sap.ui.require.toUrl('lunchydocumentsmaterialtypes') + '/test/flpSandbox.html#lunchydocumentsmaterialtypes-tile',
        pages: {
			onThelunchyDocumentMaterialTypesList: lunchyDocumentMaterialTypesList,
			onThelunchyDocumentMaterialTypesObjectPage: lunchyDocumentMaterialTypesObjectPage
        },
        async: true
    });

    return runner;
});

