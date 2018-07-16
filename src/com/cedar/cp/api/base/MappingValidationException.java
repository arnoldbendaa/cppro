/**
 * 
 */
package com.cedar.cp.api.base;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class MappingValidationException extends ValidationException {

    /**
     * 
     */
    private static final long serialVersionUID = -1334102984691679618L;

    public MappingValidationException(String reason) {
        super(reason);
    }

}
