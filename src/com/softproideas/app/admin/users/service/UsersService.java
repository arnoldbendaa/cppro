/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.admin.users.service;

import java.sql.SQLException;
import java.util.List;

import com.softproideas.app.admin.users.model.UserDetailsDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.tree.NodeLazyDTO;

public interface UsersService {

    List<NodeLazyDTO> browseSecurityModelRoot() throws ServiceException;

    List<NodeLazyDTO> browseSecurityModels(int userId, int modelId) throws ServiceException;

    List<NodeLazyDTO> browseSecurityStructureElement(int userId, int structureId, int structureElementId) throws ServiceException;

    /**
     * Returns list of available users
     */
    List<UserCoreDTO> browseUsers() throws ServiceException;

    /**
     * Save user details
     * @throws SQLException 
     */
    ResponseMessage save(int usersId, UserDetailsDTO user) throws ServiceException, SQLException;

    /**
     * Return one user for details
     */
    UserDetailsDTO fetchUsersDetails(int userId) throws ServiceException;

    /**
     * Delete user from database
     */
    ResponseMessage delete(int usersId) throws ServiceException;

}