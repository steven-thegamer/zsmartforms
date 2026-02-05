namespace db.types;

type DeliveryStatus : String(10) enum {
    OnTime = 'On Time';
    Delayed = 'Delayed';
    Pending = 'Pending';
}