import http from "./http";

const BASE_URL = "/category";

export const categoryApi = {

  /** --- Query --- */
  findAll: () => http.get(`${BASE_URL}`),

}

export default categoryApi;
