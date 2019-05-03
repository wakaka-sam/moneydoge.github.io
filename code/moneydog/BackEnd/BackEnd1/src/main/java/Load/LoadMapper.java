package Load;

import java.util.List;

public interface LoadMapper {
    List<expressage> downLoadExpressage(int pid );
    List<errand> downLoadErrands(int rid);
    List<for_help> downLoadFor_help(int fid);
    List<second_hand> downLoadSecond_hand(int sid);

    List<expressage> OnLoadExpressage();
    List<errand> OnLoadErrands();
    List<for_help> OnLoadFor_help();
    List<second_hand> OnLoadSecond_hand();

}
