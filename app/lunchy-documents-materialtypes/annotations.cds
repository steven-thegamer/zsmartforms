using MaterialTypeService as service from '../../srv/services';
annotate service.lunchyDocumentMaterialTypes with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : '{i18n>MaterialType}',
                Value : materialType,
            },
            {
                $Type : 'UI.DataField',
                Value : language_code,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>MaterialTypeDescription}',
                Value : MaterialTypeDescription,
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
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Label : '{i18n>MaterialType}',
            Value : materialType,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Language}',
            Value : language_code,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Description}',
            Value : MaterialTypeDescription,
        },
    ],
    UI.HeaderInfo : {
        TypeName : '{i18n>MaterialType}',
        TypeNamePlural : '{i18n>MaterialTypes}',
    },
);

annotate service.lunchyDocumentMaterialTypes with {
    MaterialTypeDescription @(
        UI.MultiLineText : true,
        Common.FieldControl : #Optional,
    )
};

annotate service.lunchyDocumentMaterialTypes with {
    materialType @Common.FieldControl : #ReadOnly
};

annotate service.lunchyDocumentMaterialTypes with {
    language @Common.FieldControl : #ReadOnly
};

