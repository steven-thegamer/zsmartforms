namespace db.types;

type DocumentStatus : String(10) enum {
    Printed = 'Printed';
    Synced = 'Synced';
    Created = 'Created';
}

type DeliveryStatus : String(10) enum {
    OnTime = 'On Time';
    Delayed = 'Delayed';
    Pending = 'Pending';
}