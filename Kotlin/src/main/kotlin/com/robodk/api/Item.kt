package com.robodk.api

import com.robodk.api.model.*
import org.jblas.DoubleMatrix
import java.awt.Color
import java.util.*

interface Item {

    val itemId: Long

    /** @return The RoboDK link Robolink(). */
    val rdk: RoboDkLink

    /** @return the type of an item (robot, object, target, reference frame, ...) */
    val itemType: ItemType

    /**
     * Checks if the item is valid. An invalid item will be returned by an unsuccessful function call.
     * @return true if valid, false if invalid
     */
    val valid: Boolean

    /**
     * Returns 1 if the item is visible, otherwise, returns 0.
     * @return true if visible, false if not visible
     */
    val visible: Boolean

    /**
     * Returns a list of the item childs that are attached to the provided item.
     * @return item x n -> list of child items
     */
    val children: List<Item>

    /**
     * Retrieve the currently selected feature for this object (surface, point, line, ...)
     *
     * @param featureType The type of geometry, FEATURE_SURFACE, FEATURE_POINT, ...
     * @param featureId The internal ID to retrieve the raw geometry (use GetPoints)
     * @return True if the object is selected
     */
    val selectedFeature: Triple<Boolean, ObjectSelectionType, Int>

    /**
     * Returns the home joints of a robot. These joints can be manually set in the robot "Parameters" menu, then select
     * "Set home position"
     * Sets the current joints of a robot or the joints of a target. It the item is a cartesian target, it returns the
     * preferred joints (configuration) to go to that cartesian position.

     * @return double x n -> joints array
     */
    val jointsHome: DoubleArray

    /** @return The joint limits of a robot (upper joint limits, lower joint limits) */
    val jointLimits: Pair<DoubleArray, DoubleArray>

    /**
     * Checks if a robot or program is currently running (busy or moving)
     *
     * @return busy status (true=moving, false=stopped)
     */
    val busy: Boolean

    /** @return the number of instructions of a program. */
    val instructionCount: Int

    /** The parent item of this item. */
    var parent: Item

    /**
     * Returns the name of an item. The name of the item is always displayed in the RoboDK station tree

     * @return name of the item
     */
    var name: String

    /**
     * The local position (pose) of an object, target or reference frame. For example, the position of an
     * object/frame/target with respect to its parent.
     * If a robot is provided, this will be the pose of the end effector.
     *
     * @param pose 4x4 homogeneous matrix
     */
    var pose: DoubleMatrix

    /**
     * The position (pose) the object geometry with respect to its own reference frame. This procedure works for
     * tools and objects.
     *
     * @param pose 4x4 homogeneous matrix
     */
    var geometryPose: DoubleMatrix

    /**
     * Returns the tool pose of an item. If a robot is provided it will get the tool pose of the active tool held by
     * the robot.
     *
     * @return 4x4 homogeneous matrix (pose)
     */
    var poseTool: DoubleMatrix

    /**
     * The reference frame pose of an item. If a robot is provided it will get the tool pose of the active
     * reference frame used by the robot.
     *
     * @return 4x4 homogeneous matrix (pose)
     */
    var poseFrame: DoubleMatrix

    /**
     * Sets the global position (pose) of an item. For example, the position of an object/frame/target with respect to
     * the station origin.
     * Returns the global position (pose) of an item. For example, the position of an object/frame/target with respect
     * to the station origin.

     * @param pose 4x4 homogeneous matrix (pose)
     */
    var poseAbs: DoubleMatrix

    /** The color of an object, tool or robot. */
    var color: Color

    /**
     * The current joints of a robot or the joints of a target. If the item is a cartesian target, it returns the
     * preferred joints (configuration) to go to that cartesian position.
     *
     * @return double x n -> joints matrix
     */
    var joints: DoubleArray

    /**
     * Make a copy of the item with a new roboDK link.
     *
     * @param connectionLink RoboDK link
     * @return new item
     */
    fun clone(connectionLink: RoboDk): Item

    /**
     * Update item flags.
     * Item flags allow defining how much access the user has to item-specific features.
     * @param itemFlags Item Flags to be set
     */
    fun setItemFlags(itemFlags: EnumSet<ItemFlags>)

    /**
     * Retrieve current item flags.
     * Item flags allow defining how much access the user has to item-specific features.
     * @return Current Item Flags
     */
    fun getItemFlags(): EnumSet<ItemFlags>

    /**
     * Compare this item with other item.
     * @param otherItem
     * @return True if this item and other item is the same RoboDK item.
     */
    fun equals(otherItem: Item): Boolean

    /**
     * Create a new communication link. Use this for robots if you use a multithread application running multiple
     * robots at the same time.
     */
    fun newLink()

    /**
     * Save a station or object to a file
     */
    fun save(filename: String)

    /** Deletes an item and its childs from the station. */
    fun delete();

    /**
     * Attaches the item to another parent while maintaining the current absolute position in the station.
     * The relationship between this item and its parent is changed to maintain the abosolute position.
     * @param parent parent item to attach this item
     */
    fun setParentStatic(parent: Item);

    /**
     * Attach the closest object to the tool.
     * Returns the item that was attached.
     * Use item.Valid() to check if an object was attached to the tool.
     */
    fun attachClosest(): Item

    /**
     * Detach the closest object attached to the tool (see also: setParentStatic).
     * @param parent New parent item to attach, such as a reference frame(optional). If not provided, the items held by
     * the tool will be placed at the station root.
     */
    fun detachClosest(parent: Item? = null): Item

    /**
     * Detaches any object attached to a tool.
     * @param parent New parent item to attach, such as a reference frame(optional). If not provided, the items held
     * by the tool will be placed at the station root.
     */
    fun detachAll(parent: Item? = null)

    /**
     * Sets the item visiblity status
     *
     * @param visible
     * @param visibleFrame srt the visible reference frame (1) or not visible (0)
     */
    fun setVisible(visible: Boolean, visibleFrame: VisibleRefType = VisibleRefType.DEFAULT)

    /**
     * Show an object or a robot link as collided (red)

     * @param collided
     * @param robotLinkId
     */
    fun showAsCollided(collided: Boolean, robotLinkId: Int = 0)



    /**
     * Sets the tool of a robot or a tool object (Tool Center Point, or TCP) to a frame position.
     * To set a new pose position <seealso cref="SetPoseFrame(DoubleMatrix)"/>
     * If the item is a tool, it links the robot to the tool item.If tool is a pose, it updates the current robot TCP.

     * @param frameItem
     */
    fun setPoseFrame(frameItem: Item)

    /**
     * Sets the tool of a robot or a tool object (Tool Center Point, or TCP). The tool pose can be either an item or a
     * 4x4 DoubleMatrixrix.
     * If the item is a tool, it links the robot to the tool item.If tool is a pose, it updates the current robot TCP.

     * @param toolItem
     */
    fun setPoseTool(toolItem: Item)

    /**
     * Changes the color of a robot/object/tool. A color must must in the format COLOR=[R,G,B,(A=1)] where all values
     * range from 0 to 1.
     * Alpha (A) defaults to 1 (100% opaque). Set A to 0 to make an object transparent.

     * @param tocolor color to change to
     * @param fromcolor filter by this color
     * @param tolerance optional tolerance to use if a color filter is used (defaults to 0.1)
     */
    fun recolor(
        toColor: DoubleArray,
        fromColor: DoubleArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0),
        tolerance: Double = 0.1
    )

    /**
     * Changes the color of a robot/object/tool. A color must must in the format COLOR=[R,G,B,(A=1)] where all values
     * range from 0 to 1.
     * Alpha (A) defaults to 1 (100% opaque). Set A to 0 to make an object transparent.

     * @param tocolor color to change to
     * @param fromcolor filter by this color
     * @param tolerance optional tolerance to use if a color filter is used (defaults to 0.1)
     */
    fun recolor(toColor: Color, fromColor: Color = Color(0, 0, 0, 0), tolerance: Double = 0.1)

    /**
     * Set the color of an object shape. It can also be used for tools. A color must in the format
     * COLOR=[R, G, B,(A = 1)] where all values range from 0 to 1.
     * Return the color of an Item (object, tool or robot). If the item has multiple colors it returns the first color
     * available).
     * A color is in the format COLOR = [R, G, B,(A = 1)] where all values range from 0 to 1.

     * @param shapeId ID of the shape: the ID is the order in which the shape was added using AddShape()
     * @param tocolor color to set
     */
    fun setColor(shapeId: Int, tocolor: Color)

    /**
     * Set the alpha channel of an object, tool or robot.
     * The alpha channel must remain between 0 and 1.

     * @param alpha transparency level
     */
    fun setTransparency(alpha: Double)

    /**
     * Apply a scale to an object to make it bigger or smaller.
     * The scale can be uniform (if scale is a float value) or per axis (if scale is a vector).

     * @param scale scale to apply as [scale_x, scale_y, scale_z]
     */
    fun scale(scale: DoubleArray)

    /**
     * Adds a curve provided point coordinates. The provided points must be a list of vertices. A vertex normal can be
     * provided optionally.

     * @param curvePoints matrix 3xN or 6xN -> N must be multiple of 3
     * @param addToRef add_to_ref -> If True, the curve will be added as part of the object in the RoboDK item tree
     * @param projectionType Type of projection. For example: PROJECTION_ALONG_NORMAL_RECALC will project along the
     * point normal and recalculate the normal vector on the surface projected.
     *
     * @return returns the object where the curve was added or null if failed
     */
    fun addCurve(
        curvePoints: DoubleMatrix,
        addToRef: Boolean = false,
        projectionType: ProjectionType = ProjectionType.ALONG_NORMAL_RECALC
    ): Item

    /**
     * Projects a point to the object given its coordinates. The provided points must be a list of [XYZ] coordinates.
     * Optionally, a vertex normal can be provided [XYZijk].

     * @param points matrix 3xN or 6xN -> list of points to project
     * @param projectionType
     * projection_type -> Type of projection. For example: ProjectionType.AlongNormalRecalc will
     * project along the point normal and recalculate the normal vector on the surface projected.
     *
     * @return projected points (empty matrix if failed)
     */
    fun projectPoints(points: DoubleMatrix, projectionType: ProjectionType = ProjectionType.ALONG_NORMAL_RECALC): DoubleMatrix


    /**
     * Retrieves the point under the mouse cursor, a curve or the 3D points of an object. The points are provided in
     * [XYZijk] format in relative coordinates. The XYZ are the local point coordinate and ijk is the normal of the
     * surface.
     * @param featureType The type of geometry (FEATURE_SURFACE, FEATURE_POINT, ...). Set to FEATURE_SURFACE and if
     * not point or curve was selected, the name of the geometry will be 'point on surface'
     * @param featureId The internal ID to retrieve the right geometry from the object (use SelectedFeature)
     * @param pointList The point or a list of points as XYZijk, coordinates are relative to the object (ijk is the
     * normal to the surface)
     * @return The name of the selected geometry (if applicable)
     */
    fun getPoints(featureType: ObjectSelectionType, featureId: Int): Pair<String, DoubleMatrix>

    /**
     * Update the robot milling path input and parameters. Parameter input can be an NC file (G-code or APT file) or
     * an object item in RoboDK. A curve or a point follow project will be automatically set up for a robot
     * manufacturing project.
     * Tip: Use getLink() and setLink() to get/set the robot tool, reference frame, robot and program linked to the
     * project.
     * Tip: Use setPose() and setJoints() to update the path to tool orientation or the preferred start joints.
     * @param ncfile path to the NC (G-code/APT/Point cloud) file to load (optional)
     * @param partObj
     * @param options Additional options (optional)
     * @return Program (null). Use Update() to retrieve the result
     */
    fun setMachiningParameters(ncfile: String = "", partObj: Item? = null, options: String = ""): Item

    /**
     * Sets a target as a cartesian target. A cartesian target moves to cartesian coordinates.
     */
    fun setAsCartesianTarget()

    /**
     * Sets a target as a joint target. A joint target moves to a joints position without regarding the cartesian
     * coordinates.
     */
    fun setAsJointTarget()

    /**
     * Returns an item pointer (:class:`.Item`) to a robot link. This is useful to show/hide certain robot links or
     * alter their geometry.
     * @param linkId link index(0 for the robot base, 1 for the first link, ...)
     * @return
     */
    fun objectLink(linkId: Int = 0): Item

    /**
     * Returns an item pointer (Item class) to a robot, object, tool or program. This is useful to retrieve the
     * relationship between programs, robots, tools and other specific projects.
     * @param typeLinked type of linked object to retrieve
     * @return
     */
    fun getLink(typeLinked: ItemType = ItemType.ROBOT): Item


    /**
     * Sets the robot of a program or a target. You must set the robot linked to a program or a target every time you
     * copy paste these objects.
     * If the robot is not provided, the first available robot will be chosen automatically.

     * @param robot Robot item
     */
    fun setRobot(robot: Item? = null)

    /**
     * Obsolete: Use setPoseFrame instead.
     * Sets the frame of a robot (user frame). The frame can be either an item or a 4x4 DoubleMatrixrix.
     * If "frame" is an item, it links the robot to the frame item. If frame is a 4x4 DoubleMatrixrix, it updates the linked pose
     * of the robot frame.

     * @param frame item/pose -> frame item or 4x4 DoubleMatrixrix (pose of the reference frame)
     */
    fun setFrame(frame: Item)

    /**
     * Obsolete: Use setPoseFrame instead.
     * Sets the frame of a robot (user frame). The frame can be either an item or a 4x4 DoubleMatrixrix.
     * If "frame" is an item, it links the robot to the frame item. If frame is a 4x4 DoubleMatrixrix, it updates the linked pose
     * of the robot frame.

     * @param frame item/pose -> frame item or 4x4 DoubleMatrixrix (pose of the reference frame)
     */
    fun setFrame(frame: DoubleMatrix)

    /**
     * Obsolete: Use setPoseTool instead.
     * Sets the tool pose of a robot. The tool pose can be either an item or a 4x4 DoubleMatrixrix.
     * If "tool" is an item, it links the robot to the tool item. If tool is a 4x4 DoubleMatrixrix, it updates the linked pose of
     * the robot tool.

     * @param tool item/pose -> tool item or 4x4 DoubleMatrixrix (pose of the tool frame)
     */
    fun setTool(tool: Item);

    /**
     * Obsolete: Use setPoseTool instead.
     * Sets the tool pose of a robot. The tool pose can be either an item or a 4x4 DoubleMatrixrix.
     * If "tool" is an item, it links the robot to the tool item. If tool is a 4x4 DoubleMatrixrix, it updates the linked pose of
     * the robot tool.

     * @param tool item/pose -> tool item or 4x4 DoubleMatrixrix (pose of the tool frame)
     */
    fun setTool(tool: DoubleMatrix);

    /**
     * Adds an empty tool to the robot provided the tool pose (4x4 DoubleMatrixrix) and the tool name.

     * @param toolPose
     * @param toolName
     * @return new item created
     */
    fun addTool(toolPose: DoubleMatrix, toolName: String = "New TCP"): Item

    /**
     * Computes the forward kinematics of the robot for the provided joints. The tool and the reference frame are not
     * taken into account.

     * @param joints
     * @return 4x4 homogeneous matrix: pose of the robot flange with respect to the robot base
     */
    fun solveFK(joints: DoubleArray): DoubleMatrix

    /**
     * Returns the robot configuration state for a set of robot joints.

     * @param joints array of joints
     * @return 3-array -> configuration status as [REAR, LOWERARM, FLIP]
     */
    fun jointsConfig(joints: DoubleArray): DoubleArray

    /**
     * Computes the inverse kinematics for the specified robot and pose. The joints returned are the closest to the
     * current robot configuration (see SolveIK_All())

     * @param pose 4x4 matrix -> pose of the robot flange with respect to the robot base frame
     * @param jointsApprox Aproximate solution. Leave empty to return the closest match to the current robot position.
     * @param tool 4x4 matrix -> Optionally provide a tool, otherwise, the robot flange is used.
     * Tip: use robot.PoseTool() to retrieve the active robot tool.
     * @param reference 4x4 matrix -> Optionally provide a reference, otherwise, the robot base is used.
     * Tip: use robot.PoseFrame() to retrieve the active robot reference frame.
     * @return array of joints
     */
    fun solveIK(pose: DoubleMatrix, jointsApprox: DoubleArray? = null, tool: DoubleMatrix? = null, reference: DoubleMatrix? = null): DoubleArray

    /**
     * Computes the inverse kinematics for the specified robot and pose. The function returns all available joint
     * solutions as a 2D matrix.

     * @param pose 4x4 matrix -> pose of the robot tool with respect to the robot frame
     * @param tool 4x4 matrix -> Optionally provide a tool, otherwise, the robot flange is used.
     * Tip: use robot.PoseTool() to retrieve the active robot tool.
     * @param reference 4x4 matrix -> Optionally provide a reference, otherwise, the robot base is used.
     * Tip: use robot.PoseFrame() to retrieve the active robot reference frame.
     * @return double x n x m -> joint list (2D matrix)
     */
    fun solveIK_All(pose: DoubleMatrix, tool: DoubleMatrix? = null, reference: DoubleMatrix? = null): DoubleMatrix

    /**
     * Connect to a real robot using the robot driver.

     * @param robotIp IP of the robot to connect. Leave empty to use the one defined in RoboDK
     * @return status -> true if connected successfully, false if connection failed
     */
    fun connect(robotIp: String = ""): Boolean

    /**
     * Disconnect from a real robot (when the robot driver is used)

     * @return status -> true if disconnected successfully, false if it failed. It can fail if it was previously
     * disconnected manually for example.
     */
    fun disconnect(): Boolean

    /**
     * Moves a robot to a specific target ("Move Joint" mode). By default, this function blocks until the robot
     * finishes its movements.
     * Given a target item, MoveJ can also be applied to programs and a new movement instruction will be added.
     * @param itemtarget target -> target to move to as a target item (RoboDK target item)
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveJ(itemtarget: Item, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Joint" mode). By default, this function blocks until the robot finishes
     * its movements.

     * @param joints joints -> joint target to move to.
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveJ(joints: DoubleArray, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Joint" mode). By default, this function blocks until the robot finishes
     * its movements.
     * @param target pose -> pose target to move to. It must be a 4x4 Homogeneous matrix
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveJ(target: DoubleMatrix, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Linear" mode). By default, this function blocks until the robot
     * finishes its movements.
     * Given a target item, MoveL can also be applied to programs and a new movement instruction will be added.
     * @param itemtarget target -> target to move to as a target item (RoboDK target item)
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveL(itemTarget: Item, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Linear" mode). By default, this function blocks until the robot
     * finishes its movements.
     * @param joints joints -> joint target to move to.
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveL(joints: DoubleArray, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Linear" mode). By default, this function blocks until the robot
     * finishes its movements.
     * @param target pose -> pose target to move to. It must be a 4x4 Homogeneous matrix
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveL(target: DoubleMatrix, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Circular" mode). By default, this function blocks until the robot
     * finishes its movements.

     * @param itemtarget1 target -> intermediate target to move to as a target item (RoboDK target item)
     * @param itemtarget2 target -> final target to move to as a target item (RoboDK target item)
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveC(itemTarget1: Item, itemTarget2: Item, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Circular" mode). By default, this function blocks until the robot
     * finishes its movements.

     * @param joints1 joints -> intermediate joint target to move to.
     * @param joints2 joints -> final joint target to move to.
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveC(joints1: DoubleArray, joints2: DoubleArray, blocking: Boolean = true)

    /**
     * Moves a robot to a specific target ("Move Circular" mode). By default, this function blocks until the robot
     * finishes its movements.

     * @param target1 pose -> intermediate pose target to move to. It must be a 4x4 Homogeneous matrix
     * @param target2 pose -> final pose target to move to. It must be a 4x4 Homogeneous matrix
     * @param blocking blocking -> True if we want the instruction to block until the robot finished the movement
     * (default=true)
     */
    fun moveC(target1: DoubleMatrix, target2: DoubleMatrix, blocking: Boolean = true)

    /**
     * Checks if a joint movement is free of collision.

     * @param j1 joints -> start joints
     * @param j2 joints -> destination joints
     * @param minstepDeg (optional): maximum joint step in degrees
     * @returncollision : returns 0 if the movement is free of collision. Otherwise it returns the number of pairs of
     * objects that collided if there was a collision.
     */
    fun moveJ_Test(j1: DoubleArray, j2: DoubleArray, minstepDeg: Double = -1.0): Int

    /**
     * Checks if a joint movement is free of collision.

     * @param j1 joints -> start joints
     * @param j2 joints -> joints via
     * @param j3 joints -> joints final destination
     * @param blendDeg Blend in degrees
     * @param minstepDeg (optional): maximum joint step in degrees
     * @return collision : returns false if the movement is possible and free of collision. Otherwise it returns true.
     */
    fun moveJ_Test_Blend(
        j1: DoubleArray,
        j2: DoubleArray,
        j3: DoubleArray,
        blendDeg: Double = 5.0,
        minstepDeg: Double = -1.0
    ): Boolean

    /**
     * Checks if a linear movement is free of collision.
     * @param j1 joints -> start joints
     * @param j2 joints -> destination joints
     * @param minstepDeg (optional): maximum joint step in degrees
     * @return collision : returns 0 if the movement is free of collision. Otherwise it returns the number of pairs of
     * objects that collided if there was a collision.
     */
    fun moveL_Test(j1: DoubleArray, j2: DoubleArray, minstepDeg: Double = -1.0): Int

    /**
     * Sets the speed and/or the acceleration of a robot.
     * @param speedLinear linear speed in mm/s (-1 = no change)
     * @param accelLinear linear acceleration in mm/s2 (-1 = no change)
     * @param speedJoints joint speed in deg/s (-1 = no change)
     * @param accelJoints joint acceleration in deg/s2 (-1 = no change)
     */
    fun setSpeed(
        speedLinear: Double,
        accelLinear: Double = -1.0,
        speedJoints: Double = -1.0,
        accelJoints: Double = -1.0
    )

    /**
     * Sets the rounding accuracy to smooth the edges of corners.
     * In general, it is recommended to allow a small approximation near the corners to maintain a constant speed.
     * Setting a rounding values greater than 0 helps avoiding jerky movements caused by constant acceleration and
     * decelerations.
     * @param rounding
     */
    fun setRounding(rounding: Double)

    /**
     * Displays a sequence of joints
     * @param sequence joint sequence as a 6xN matrix or instruction sequence as a 7xN matrix
     */
    fun showSequence(sequence: DoubleMatrix)


    /** Stops a program or a robot */
    fun stop()

    /**
     * Waits (blocks) until the robot finishes its movement.
     * @param timeoutSec timeout -> Max time to wait for robot to finish its movement (in seconds)
     */
    fun waitMove(timeoutSec: Double = 300.0)

    /**
     * Saves a program to a file.
     * @param filename File path of the program
     * @param runMode RUNMODE_MAKE_ROBOTPROG to generate the program file.Alternatively,
     * Use RUNMODE_MAKE_ROBOTPROG_AND_UPLOAD or RUNMODE_MAKE_ROBOTPROG_AND_START to transfer the program through FTP
     * and execute the program.
     * @return Transfer succeeded is True if there was a successful program transfer
     * (if RUNMODE_MAKE_ROBOTPROG_AND_UPLOAD or RUNMODE_MAKE_ROBOTPROG_AND_START are used)
     */
    fun makeProgram(filename: String = "", runMode: RunMode = RunMode.MAKE_ROBOT_PROGRAM): Boolean

    /**
     * Sets if the program will be run in simulation mode or on the real robot.
     * Use: "PROGRAM_RUN_ON_SIMULATOR" to set the program to run on the simulator only or "PROGRAM_RUN_ON_ROBOT" to
     * force the program to run on the robot.
     * @return number of instructions that can be executed
     */
    fun setRunType(programExecutionType: ProgramExecutionType)

    /**
     * Runs a program. It returns the number of instructions that can be executed successfully (a quick program check is
     * performed before the program starts)
     * This is a non-blocking call. Use IsBusy() to check if the program execution finished.
     * Notes:
     * if setRunMode(RUNMODE_SIMULATE) is used  -> the program will be simulated (default run mode)
     * if setRunMode(RUNMODE_RUN_ROBOT) is used -> the program will run on the robot (default when RUNMODE_RUN_ROBOT is
     * used)
     * if setRunMode(RUNMODE_RUN_ROBOT) is used together with program.setRunType(PROGRAM_RUN_ON_ROBOT) -> the program
     * willrun sequentially on the robot the same way as if we right clicked the program and selected "Run on robot"
     * in the RoboDK GUI
     * @return number of instructions that can be executed
     */
    fun runProgram(): Int

    /**
     * Runs a program. It returns the number of instructions that can be executed successfully (a quick program check is
     * performed before the program starts)
     * Program parameters can be provided for Python calls.
     * This is a non-blocking call.Use IsBusy() to check if the program execution finished.
     * Notes: if setRunMode(RUNMODE_SIMULATE) is used  -> the program will be simulated (default run mode)
     * if setRunMode(RUNMODE_RUN_ROBOT) is used ->the program will run on the robot(default when RUNMODE_RUN_ROBOT is
     * used)
     * if setRunMode(RUNMODE_RUN_ROBOT) is used together with program.setRunType(PROGRAM_RUN_ON_ROBOT) -> the program
     * will run sequentially on the robot the same way as if we right clicked the program and selected "Run on robot"
     * in the RoboDK GUI
     * @param parameters Number of instructions that can be executed
     */
    fun runCode(parameters: String? = null): Int

    /**
     * Adds a program call, code, message or comment to the program. Returns True if succeeded.
     * @param code string of the code or program to run
     * @param runType specify if the code is a program
     * @return True if success; False othwersise
     */
    fun runCodeCustom(code: String, runType: ProgramRunType = ProgramRunType.CALL_PROGRAM): Boolean

    /**
     * Generates a pause instruction for a robot or a program when generating code. Set it to -1 (default) if you want
     * the robot to stop and let the user resume the program anytime.
     * @param timeMs Time in milliseconds
     */
    fun pause(timeMs: Double = -1.0);

    /**
     * Sets a variable (output) to a given value. This can also be used to set any variables to a desired value.
     * @param ioVar io_var -> digital output (string or number)
     * @param ioValue io_value -> value (string or number)
     */
    fun setDO(ioVar: String, ioValue: String)

    /**
     * Waits for an input io_id to attain a given value io_value. Optionally, a timeout can be provided.
     * @param ioVar io_var -> digital output (string or number)
     * @param ioValue io_value -> value (string or number)
     * @param timeoutMs int (optional) -> timeout in miliseconds
     */
    fun waitDI(ioVar: String, ioValue: String, timeoutMs: Double = -1.0)

    /**
     * Add a custom instruction. This instruction will execute a Python file or an executable file.
     * @param name digital input (string or number)
     * @param pathRun path to run(relative to RoboDK/bin folder or absolute path)
     * @param pathIcon icon path(relative to RoboDK/bin folder or absolute path)
     * @param blocking True if blocking, 0 if it is a non blocking executable trigger
     * @param cmdRunOnRobot Command to run through the driver when connected to the robot
     */
    fun addCustomInstruction(
        name: String, pathRun: String, pathIcon: String = "", blocking: Boolean = true, cmdRunOnRobot: String = ""
    )

    /**
     * Adds a new robot move joint instruction to a program. Obsolete. Use MoveJ instead.
     * @param itemtarget target to move to
     */
    fun addMoveJ(itemTarget: Item)

    /**
     * Adds a new robot move linear instruction to a program. Obsolete. Use MoveL instead.
     * @param itemtarget target to move to
     */
    fun addMoveL(itemTarget: Item)

    /** Show or hide instruction items of a program in the RoboDK tree */
    fun showInstructions(show: Boolean = true)

    /** Show or hide targets of a program in the RoboDK tree */
    fun showTargets(show: Boolean = true)

    /**
     * Returns the program instruction at position id
     * @param instructionId
     * @return program instruction at position instructionId
     */
    fun getInstruction(instructionId: Int): ProgramInstruction

    /**
     * Sets the program instruction at position id
     * @param instructionId
     * @param instruction
     */
    fun setInstruction(instructionId: Int, instruction: ProgramInstruction)

    fun update(): UpdateResult

    /**
     * Updates a program and returns the estimated time and the number of valid instructions.
     * An update can also be applied to a robot machining project. The update is performed on the generated program.
     * @param collisionCheck check_collisions: Check collisions (COLLISION_ON -yes- or COLLISION_OFF -no-)
     * @param timeoutSec Maximum time to wait for the update to complete (in seconds)
     * @param linStepMm Maximum step in millimeters for linear movements (millimeters). Set to -1 to use the default,
     * as specified in Tools-Options-Motion.
     * @param jointStepDeg Maximum step for joint movements (degrees). Set to -1 to use the default, as specified in
     * Tools-Options-Motion.
     * @return 1.0 if there are no problems with the path or less than 1.0 if there is a problem in the path (ratio
     * of problem)
     */
    fun update(
        collisionCheck: CollisionCheckOptions, /* = CollisionCheckOptions.CollisionCheckOff, */
        timeoutSec: Int = 3600,
        linStepMm: Double = -1.0,
        jointStepDeg: Double = -1.0
    ): UpdateResult

    /**
     * Returns the list of program instructions as an MxN matrix, where N is the number of instructions and M equals
     * to 1 plus the number of robot axes.
     * @param instructions the matrix of instructions
     * @return Returns 0 if success
     */
    fun instructionList(): Pair<Int, DoubleMatrix>

    /**
     * Returns a list of joints.
     * Linear moves are rounded according to the smoothing parameter set inside the program.
     * @param mmStep Maximum step in millimeters for linear movements (millimeters)
     * @param degStep Maximum step for joint movements (degrees)
     * @param saveToFile Provide a file name to directly save the output to a file. If the file name is not provided it
     * will return the matrix. If step values are very small, the returned matrix can be very large.
     * @param collisionCheck Check for collisions: will set to 1 or 0
     * @param flags Reserved for future compatibility
     * @param timeoutSec Maximum time to wait for the result (in seconds)
     * @param time_step Time step for time-based calculation (ListJointsType must be set to TimeBased)
     * @return List of InstructionListJointsResult.
     */
    fun getInstructionListJoints(
        mmStep: Double = 10.0,
        degStep: Double = 5.0,
        saveToFile: String = "",
        collisionCheck: CollisionCheckOptions = CollisionCheckOptions.COLLISION_CHECK_OFF,
        flags: ListJointsType = ListJointsType.ANY,
        timeoutSec: Int = 3600,
        time_step: Double = 0.2
    ): InstructionListJointsResult

    /**
     * Returns a list of joints an MxN matrix, where M is the number of robot axes plus 4 columns. Linear moves are
     * rounded according to the smoothing parameter set inside the program.
     * @param mmStep Maximum step in millimeters for linear movements (millimeters)
     * @param degStep Maximum step for joint movements (degrees)
     * @param saveToFile Provide a file name to directly save the output to a file. If the file name is not provided
     * it will return the matrix. If step values are very small, the returned matrix can be very large.
     * @param collisionCheck Check for collisions: will set to 1 or 0
     * @param flags Reserved for future compatibility
     * @param timeoutSec Maximum time to wait for the result (in seconds)
     * @param time_step Time step for time-based calculation (ListJointsType must be set to TimeBased)
     * @return (0 if success, otherwise, it will return negative values, a human readable error message (if any),
     * the list of joints as [J1, J2, ..., Jn, ERROR, MM_STEP, DEG_STEP, MOVE_ID] if a file name is not specified)
     */
    fun instructionListJoints(
        mmStep: Double = 10.0,
        degStep: Double = 5.0,
        saveToFile: String = "",
        collisionCheck: CollisionCheckOptions = CollisionCheckOptions.COLLISION_CHECK_OFF,
        flags: ListJointsType = ListJointsType.ANY,
        timeoutSec: Int = 3600,
        time_step: Double = 0.2
    ): Triple<Int, String, DoubleMatrix>

    /** Disconnect from the RoboDK API. This flushes any pending program generation. */
    fun finish()
}