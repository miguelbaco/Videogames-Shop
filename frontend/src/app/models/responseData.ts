import { Error } from "./error";

export class ResponseData {
    data: any;
    error: Array<Error>;
    meta: string;
}

