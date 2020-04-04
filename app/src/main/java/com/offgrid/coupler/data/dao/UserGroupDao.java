package com.offgrid.coupler.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.offgrid.coupler.data.entity.GroupUsers;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserGroupRef;

import java.util.ArrayList;
import java.util.List;


@Dao
public abstract class UserGroupDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(UserGroupRef ref);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<UserGroupRef> refs);

    @Delete
    public abstract void delete(UserGroupRef ref);

    @Query("delete from T_User_Group_Ref where group_id =:group_id")
    abstract void _deleteByGroupId(long group_id);


    @Transaction
    public void upsert(GroupUsers groupUsers) {
        _deleteByGroupId(groupUsers.group.getId());
        if (!groupUsers.users.isEmpty()) {
            List<UserGroupRef> refs = new ArrayList<>();
            for (User user : groupUsers.users) {
                refs.add(new UserGroupRef(user.getId(), groupUsers.group.getId()));
            }
            insert(refs);
        }
    }

}
