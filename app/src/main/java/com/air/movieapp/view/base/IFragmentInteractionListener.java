/*
 * *
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  *
 *  * @File: IFragmentInteractionListener.java
 *  * @Project: Devote
 *  * @Abstract:
 *  * @Copyright: Copyright © 2015, Saregama India Ltd. All Rights Reserved
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *  * <p/>
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 */

package com.air.movieapp.view.base;

public interface IFragmentInteractionListener {

 /*   public void loadFragment(int fragmentContainerId, BaseFragment fragment,
                             @Nullable String tag, int enterAnimId, int exitAnimId,
                             BaseFragment.FragmentTransactionType fragmentTransactionType);*/

    public void loadFragment(int fragmentContainerId, BaseFragment fragment);


}
