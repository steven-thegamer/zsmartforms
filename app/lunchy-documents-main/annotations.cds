using MainService as service from '../../srv/services';
annotate service.lunchyDocumentHeaders with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Documentnumber}',
                Value : documentNumber,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Incoterms1}',
                Value : incoterms1,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Incoterms2}',
                Value : incoterms2,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Route}',
                Value : route,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>DeliveryDate}',
                Value : deliveryDate,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>GoodsIssueDate}',
                Value : goodsIssueDate,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>GoodsIssueTime}',
                Value : goodsIssueTime,
            },
        ],
    },
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'GeneratedFacet1',
            Label : 'General Information',
            Target : '@UI.FieldGroup#GeneratedGroup',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>SoldToParty}',
            ID : 'SoldtoParty',
            Target : '@UI.FieldGroup#SoldtoParty',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : 'Ship to Party',
            ID : 'ShiptoParty',
            Target : '@UI.FieldGroup#ShiptoParty',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>Items}',
            ID : 'i18nItems',
            Target : 'items/@UI.LineItem#i18nItems',
        },
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Label : '{i18n>DocumentNumber}',
            Value : documentNumber,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Incoterms}',
            Value : incoterms,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Route}',
            Value : route,
        },
        {
            $Type : 'UI.DataField',
            Value : deliveryDate,
            Label : '{i18n>DeliveryDate}',
        },
        {
            $Type : 'UI.DataField',
            Value : deliveryStatus,
            Label : '{i18n>DeliveryStatus}',
            Criticality : deliveryStatusCriticality,
            CriticalityRepresentation : #WithIcon,
        },
        {
            $Type : 'UI.DataField',
            Value : status,
            Label : '{i18n>Status}',
            Criticality : statusCriticality,
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.downloadDocument',
            Label : '{i18n>DownloadDocument}',
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.sendEmail',
            Label : '{i18n>SendEmail}',
        },
        {
            $Type : 'UI.DataField',
            Value : shipToParty_CustomerNumber,
            Label : '{i18n>ShipToParty}',
        },
        {
            $Type : 'UI.DataField',
            Value : soldToParty_CustomerNumber,
            Label : '{i18n>SoldToParty}',
        },
    ],
    UI.HeaderInfo : {
        TypeName : '{i18n>Document}',
        TypeNamePlural : '{i18n>Documents}',
        Title : {
            $Type : 'UI.DataField',
            Value : documentNumber,
        },
        Description : {
            $Type : 'UI.DataField',
            Value : incoterms,
        },
    },
    UI.FieldGroup #SoldtoParty : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : soldToParty.Country,
                Label : '{i18n>Country}',
            },
            {
                $Type : 'UI.DataField',
                Value : soldToParty.CustomerNumber,
                Label : '{i18n>Customernumber}',
            },
            {
                $Type : 'UI.DataField',
                Value : soldToParty.Name,
                Label : '{i18n>Name}',
            },
            {
                $Type : 'UI.DataField',
                Value : soldToParty.PhoneNumber,
                Label : '{i18n>PhoneNumber}',
            },
            {
                $Type : 'UI.DataField',
                Value : soldToParty.PostalCode,
                Label : '{i18n>PostalCode}',
            },
            {
                $Type : 'UI.DataField',
                Value : soldToParty.Street,
                Label : '{i18n>Street}',
            },
        ],
    },
    UI.FieldGroup #ShiptoParty : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : shipToParty.Country,
                Label : '{i18n>Country}',
            },
            {
                $Type : 'UI.DataField',
                Value : shipToParty.CustomerNumber,
                Label : '{i18n>Customernumber}',
            },
            {
                $Type : 'UI.DataField',
                Value : shipToParty.Name,
                Label : '{i18n>Name}',
            },
            {
                $Type : 'UI.DataField',
                Value : shipToParty.PhoneNumber,
                Label : '{i18n>PhoneNumber}',
            },
            {
                $Type : 'UI.DataField',
                Value : shipToParty.PostalCode,
                Label : '{i18n>PostalCode}',
            },
            {
                $Type : 'UI.DataField',
                Value : shipToParty.Street,
                Label : '{i18n>Street}',
            },
        ],
    },
);

annotate service.lunchyDocumentHeaders with {
    soldToParty @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'lunchyDocumentSoldToParties',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : soldToParty_CustomerNumber,
                    ValueListProperty : 'CustomerNumber',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Name',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Street',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Country',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'PostalCode',
                },
            ],
        },
        Common.Text : soldToParty.Name,
        Common.Text.@UI.TextArrangement : #TextOnly,
    )
};

annotate service.lunchyDocumentHeaders with {
    shipToParty @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'lunchyDocumentShipToParties',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : shipToParty_CustomerNumber,
                    ValueListProperty : 'CustomerNumber',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Name',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Street',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Country',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'PostalCode',
                },
            ],
        },
        Common.Text : shipToParty.Name,
    )
};

annotate service.lunchyDocumentItems with @(
    UI.LineItem #i18nItems : [
        {
            $Type : 'UI.DataField',
            Value : itemNumber,
            Label : '{i18n>ItemNumber}',
        },
        {
            $Type : 'UI.DataField',
            Value : material,
            Label : '{i18n>Material}',
        },
        {
            $Type : 'UI.DataField',
            Value : materialType,
            Label : '{i18n>MaterialType}',
        },
        {
            $Type : 'UI.DataField',
            Value : materialTypeDescription,
            Label : '{i18n>MaterialTypeDescription}',
        },
        {
            $Type : 'UI.DataField',
            Value : quantityDeliveredSales,
            Label : '{i18n>QuantityDeliveredSales}',
        },
        {
            $Type : 'UI.DataField',
            Value : quantityDeliveredStockKeeping,
            Label : '{i18n>QuantityDeliveredStockKeeping}',
        },
        {
            $Type : 'UI.DataField',
            Value : shortText,
            Label : '{i18n>Misc}',
        },
    ],
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>GeneralInformation}',
            ID : 'i18nGeneralInformation',
            Target : '@UI.FieldGroup#i18nGeneralInformation',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>MaterialType}',
            ID : 'MaterialType',
            Target : '@UI.FieldGroup#MaterialType',
        },
    ],
    UI.FieldGroup #i18nGeneralInformation : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : header_documentNumber,
                Label : '{i18n>DocumentNumber}',
            },
            {
                $Type : 'UI.DataField',
                Value : itemNumber,
                Label : '{i18n>ItemNumber}',
            },
            {
                $Type : 'UI.DataField',
                Value : material,
                Label : '{i18n>Material}',
            },
            {
                $Type : 'UI.DataField',
                Value : quantityDeliveredSales,
                Label : '{i18n>QuantityDeliveredSales}',
            },
            {
                $Type : 'UI.DataField',
                Value : quantityDeliveredStockKeeping,
                Label : '{i18n>QuantityDeliveredStockKeeping}',
            },
            {
                $Type : 'UI.DataField',
                Value : shortText,
                Label : '{i18n>Misc}',
            },
            {
                $Type : 'UI.DataField',
                Value : uom,
                Label : '{i18n>UnitOfMeasurement}',
            },
        ],
    },
    UI.FieldGroup #MaterialType : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : materialType.language_code,
            },
            {
                $Type : 'UI.DataField',
                Value : materialType.materialType,
                Label : '{i18n>MaterialType}',
            },
            {
                $Type : 'UI.DataField',
                Value : materialType.MaterialTypeDescription,
                Label : '{i18n>MaterialTypeDescription}',
            },
        ],
    },
    UI.HeaderInfo : {
        TypeName : '{i18n>Item}',
        TypeNamePlural : '{i18n>Items}',
        Title : {
            $Type : 'UI.DataField',
            Value : header_documentNumber,
        },
        Description : {
            $Type : 'UI.DataField',
            Value : itemNumber,
        },
    },
);

