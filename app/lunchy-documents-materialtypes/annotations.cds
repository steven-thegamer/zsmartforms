using MaterialTypesService as service from '../../srv/services';
annotate service.MaterialTypes with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Language}',
                Value : language_code,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>MaterialType}',
                Value : materialType,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Description}',
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
    ],
);

annotate service.MaterialTypes with {
    MaterialTypeDescription @UI.MultiLineText : true
};

