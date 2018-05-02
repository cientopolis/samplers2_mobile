package cientopolis.cientopolis.interfaces;

import cientopolis.cientopolis.model.ResponseDTO;

/**
 * Created by nicolas.valentini on 4/7/17.
 * This is a interface that should implements all the fragments that need do any request
 */

public interface RequestControllerListener<T> {
    void responseOk(Integer id, ResponseDTO<T> response);
    void responseError(Integer id, ResponseDTO<T> response);
}
