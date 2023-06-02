export interface ChatData {
    roomName:string;
    id:string;
}

export const searchAPI = (keyword:string) => {
    return searchData.filter((v) => v.roomName.includes(keyword))
}

export const searchData :ChatData[] = [
    {
     "roomName": "3L_처리방_1",
     "id": "1"
    },
    {
     "roomName": "5L_처리방_1",
     "id": "2"
    },
    {
     "roomName": "3L_처리방_2",
     "id": "3"
    }
   ]
